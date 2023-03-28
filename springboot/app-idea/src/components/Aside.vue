<template>
    <el-menu :default-openeds="['1', '3']" style="min-height: 100%; overflow-x:hidden; border-right-color: transparent"
             background-color="rgb(48,65,86)"
             text-color="#fff"
             active-text-color="#ffd04b"
             :collapse-transition="false"
             :iscollapse="isCollapse"
             router
    >
        <div style="height: 60px;line-height: 60px;text-align: center">
            <img src="../assets/logo.png" alt="" style="width: 20px;position: relative; top: 5px;margin-right: 5px">
            <b style="color: white" v-show="logoTextShow">作业管理系统</b>
        </div>
        <el-menu-item index="/home">
            <i class="el-icon-s-custom"></i>
            <span slot="title">个人主页</span>
        </el-menu-item>
        <el-menu-item index="/">
            <i class="el-icon-menu"></i>
            <span slot="title">系统管理</span>
        </el-menu-item>
        <el-menu-item index="/user">
            <i class="el-icon-document"></i>
            <span slot="title">用户管理</span>
        </el-menu-item>
    </el-menu>
</template>

<script>
    export default {
        name: "Aside",
        props: {
            isCollapse: Boolean,
            logoTextShow: Boolean
        },
        data() {
            return {
                menus: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")) : [],
                opens: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")).map(v => v.id + '') : []
            }
        },
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
