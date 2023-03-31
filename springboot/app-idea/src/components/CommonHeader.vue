<template>
    <div style="font-size: 12px;line-height: 60px;display: flex">
        <div style="flex: 1;">
            <!-- <span :class="collapseBtnClass" style="cursor: pointer; font-size: 18px" @click="collapse">  <i class="el-icon-s-fold">  </i></span> -->
            <el-button style="margin-right: 20px" icon="el-icon-s-fold" size="mini" @click="collapse()"></el-button>
            <el-breadcrumb separator="/"  style="display: inline-block; margin-left: 10px">
                <el-breadcrumb-item :to="'/'" style="font-size: 16px">首页</el-breadcrumb-item>
                <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <el-dropdown style="width: 150px; cursor: pointer;  text-align: right ">
            <div style="display: inline-block">
                <img :src="user.avatarUrl"  alt="" referrerpolicy="no-referrer"
                     style="width: 30px; border-radius: 50%;position: relative;top: 10px; right: 5px">
                <span >{{ user.nickname }}</span><i class="el-icon-arrow-down" style="margin-left: 10px"></i>
            </div>
            <el-dropdown-menu slot="dropdown" style="text-align: center; width: 100px">
                <el-dropdown-item style="font-size: 15px; padding: 5px 0" @click.native="per">个人信息</el-dropdown-item>
                <el-dropdown-item style="font-size: 15px; padding: 5px 0" @click.native="logout"> 退出
                </el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
</template>
<script>
    export default {
        name: "CommonHeader",
        props: {
            collapseBtnClass: String,
            collapse:''
        },
        computed:{
            currentPathName(){
                return this.$store.state.currentPathName;//需要监听的数据
            }
        },
        data(){
            return {
                user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) :{}
            }
        },
        // watch:{
        //     currentPathName(newVal,oldVal){
        //         console.log(newVal)
        //     }
        // },
        methods:{
            logout(){
                localStorage.removeItem("user")
                this.$message.success("退出成功")
                this.$router.push("/login")
            },
            per(){
                this.$router.push("/person")
            }
        }

    }
</script>

<style scoped>


</style>

