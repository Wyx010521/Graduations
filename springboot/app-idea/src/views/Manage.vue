<template>
    <el-container style="min-height: 100vh">

        <el-aside :width="sideWidth + 'px'" style="box-shadow: 2px 0 6px rgb(0 21 41 / 35% );">
            <CommonAside :iscollapse="isCollapse" :logotextshow="logoTextShow"/>
        </el-aside>

        <el-container>
            <el-header style=" border-bottom:1px solid #ccc;">
                <CommonHeader :collapseBtnclass="collapseBtnClass" :collapse="collapse"/>
            </el-header>

            <el-main>
                <!--            //表示当前页面的子路由会在router-view里面展示-->
                <router-view/>

            </el-main>

        </el-container>

    </el-container>


</template>

<script>
    import CommonAside from "@/components/CommonAside";
    import CommonHeader from "@/components/CommonHeader";

    export default {
        name: 'home',
        // components: {
        //   Aside
        // },
        data() {
            return {
                collapseBtnClass: 'el-icon-s-fold',
                isCollapse: false,
                sideWidth: 200,//自动宽度中的原始宽度
                logoTextShow: true,
            }
        },
        components: {//注册组件
            CommonAside,
            CommonHeader
        },
        methods: {
            collapse() {/* 点击收缩按钮触发 */
                this.isCollapse = !this.isCollapse
                if (this.isCollapse) {//收缩
                    this.sideWidth = 64 //点击按钮后的宽度
                    this.collapseBtnClass = 'el-icon-s-unfold'
                    this.logoTextShow = false
                } else {//展开
                    this.sideWidth = 200
                    this.collapseBtnClass = "el-icon-s-fold"
                    this.logoTextShow = true
                }
            },
            // getUser() {
            //     let username = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).username : ""
            //     if (username) {
            //         // 从后台获取User数据
            //         this.request.get("/user/username/" + username).then(res => {
            //             // 重新赋值后台的最新User数据
            //             this.user = res.data
            //         })
            //     }
            // }
        }
    }
</script>
<style>
    .el-menu-item.is-active {
        background-color: rgb(38, 52, 69) !important;
    }

    .el-menu-item:hover {
        background-color: rgb(38, 52, 69) !important;
    }

    .el-submenu__title:hover {
        background-color: rgb(38, 52, 69) !important;
    }

    /*解决收缩菜单文字不消失问题*/
    .el-menu--collapse span {
        visibility: hidden;
    }
</style>
