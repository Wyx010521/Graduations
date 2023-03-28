<template>
    <div>
        <div style="margin: 10px 0;">
            <el-input  style="width: 200px"  placeholder="请在这里输入用户名" prefix-icon="el-icon-search"  class="mr-right-5"
                       v-model="username" ></el-input>
            <el-input  style="width: 200px"  placeholder="请在这里输入邮箱" prefix-icon="el-icon-search"  class="mr-right-5"
                       v-model="email" ></el-input>
            <el-input  style="width: 200px"  placeholder="请在这里输入电话" prefix-icon="el-icon-search"  class="mr-right-5"
                       v-model="phone" ></el-input>
            <el-button class="mr-lift-5" type="primary" @click="load">搜索</el-button>
            <el-button class="mr-lift-5" type="warning" @click="reset">清空输入</el-button>
        </div>
        <div style="margin: 10px 0;">
            <el-button type="primary" @click="handleAdd"> 新增 <i class="el-icon-circle-plus-outline"></i> </el-button>
            <el-popconfirm
                    class="mr-lift-5"
                    confirm-button-text='确定'
                    cancel-button-text='再想想'
                    icon="el-icon-info"
                    icon-color="red"
                    title="您确定批量删除这些数据吗？"
                    @confirm="delBatch"
            >
                <el-button type="danger"   class="mr-right-5" slot="reference"> 批量删除 <i class="el-icon-remove-outline"></i> </el-button>
            </el-popconfirm>
            <el-upload action="http://localhost:8090/user/import"
                       style="display: inline-block" class="mr-right-5"  :show-file-list="false"
                       accept="xlsx"
                       :on-success="handleExcelImportSuccess"
            >
            <el-button type="info" > 导入 <i class="el-icon-bottom-left"></i> </el-button>
            </el-upload>
            <el-button type="success" @click="exp"> 导出 <i class="el-icon-top-right"></i> </el-button>

        </div>

        <el-table :data="tableData " border stripe  :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange" >
            <el-table-column type="selection" width="55"> </el-table-column>
            <el-table-column prop="id" label="ID" width="80"></el-table-column>
            <el-table-column prop="username" label="用户名" width="140"></el-table-column>
            <el-table-column prop="nickname" label="昵称" width="120"></el-table-column>
            <el-table-column prop="email" label="邮箱"></el-table-column>
            <el-table-column prop="phone" label="电话"></el-table-column>
            <el-table-column prop="phone" label="微信"></el-table-column>
            <el-table-column prop="address" label="地址"></el-table-column>
            <el-table-column label="操作" width="200" align="center">
                <template slot-scope="scope">
                    <el-button type="warning" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit-outline"></i></el-button>
                    <el-popconfirm
                            class="mr-lift-5"
                            confirm-button-text='确定'
                            cancel-button-text='再想想'
                            icon="el-icon-info"
                            icon-color="red"
                            title="您确定删除吗？"
                            @confirm="handleDelet(scope.row.id)"
                    >
                        <el-button type="danger" slot="reference">删除 <i class="el-icon-delete"></i></el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <div style="padding: 10px 0">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="pageNum"
                    :page-sizes="[5 , 15, 25, 35, 50]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>

        <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="45%" >
            <el-form label-width="80px" size="small">
                <el-form-item label="用户名" >
                    <el-input v-model="form.username" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="昵称" >
                    <el-input v-model="form.nickname" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" >
                    <el-input v-model="form.email" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="电话" >
                    <el-input v-model="form.phone" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="微信" >
                    <el-input v-model="form.wechat" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="地址" >
                    <el-input v-model="form.address" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "User",
        data(){
            return{
                records:[],
                tableData: [],
                total:0,
                pageNum:1,
                pageSize:5,
                email:"",
                phone:"",
                address:"",
                nickname:"",
                avatarUrl:"",
                wechat:"",
                username:"",
                dialogFormVisible:false,
                multipleSelection:[],
                form:{},
                collapseBtnClass:'el-icon-s-fold',
                isCollapse:false,
                sideWidth:200,//自动宽度中的原始宽度
                logoTextShow:true,
                headerBg:'headerBg'
            }
        },
        created() {
            //调用请求分页查询数据 方法
            this.load()
        },
        methods:{
            load(){
                //请求分页查询数据 封装
                this.request.get("/user/page",{
                    params:{
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                        username:this.username,
                        address:this.address,
                        email:this.email,
                        phone:this.phone,
                        nickname:this.nickname
                    }
                }).then(res =>{
                    this.tableData= res.data.records
                    this.total = res.data.total
                })
            },
            handleAdd(){
                this.dialogFormVisible = true
                this.form = {}
            },
            save(){
                this.request.post("/user",this.form).then(res => {
                    if(res.data){
                        this.$message.success("保存成功")
                        this.dialogFormVisible = false
                        this.load()
                    }else {
                        this.$message.error("保存失败")
                    }
                })
            },

            handleDelet(id){//删除方法
                this.request.delete("/user/"+id).then( res =>{
                    if(res.data){
                        this.$message.success("删除成功")
                        this.load()
                    }else {
                        this.$message.error("删除失败")
                    }
                })
            },
            handleSelectionChange(val){
                console.log(val)
                this.multipleSelection = val
            },
            delBatch(){//批量删除方法
                let ids = this.multipleSelection.map( v => v.id) // [{1},{2},{3} => [1,2,3]

                this.request.delete("/user/del/batch",{data:ids}).then( res =>{
                    if(res.data){
                        this.$message.success("批量删除成功")
                        this.load()
                    }else {
                        this.$message.error("批量删除失败")
                        this.load()
                    }
                })
            },
            handleEdit(row){//编辑方法
                this.form = row
                this.dialogFormVisible = true
                this.form = JSON.parse(JSON.stringify(row))
            },
            reset(){
                this.username=""
                this.email=""
                this.phone=""
            },
            //显示数量
            handleSizeChange(pageSize){
                this.pageSize = pageSize
                //console.log(pageSize)
                this.load()
            },
            //显示页数
            handleCurrentChange(pageNum){
                this.pageNum = pageNum
                //console.log(pageNum)
                this.load()
            },
            exp(){//导出方法，调用导出接口
              window.open("http://localhost:8090/user/export")
            },
            handleExcelImportSuccess(){
                this.$message.success("导入成功")
                this.load()
            }
        }
    }
</script>

<style scoped>
    .headerBg{
        background: #cccccc !important;
        color: lightslategrey;
    }
</style>