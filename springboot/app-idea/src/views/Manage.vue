<template>
      <el-container style="min-height: 100vh">

        <el-aside :width="sideWidth + 'px'" style="box-shadow: 2px 0 6px rgb(0 21 41 / 35% );" >
          <Aside  :iscollapse="isCollapse" :logotextshow="logoTextShow"  />
        </el-aside>

        <el-container>
           <el-header style=" border-bottom:1px solid #ccc;"  >
                <Header :collapseBtnclass="collapseBtnClass" :collapse="collapse"   />
          </el-header>

          <el-main>
<!--            //表示当前页面的子路由会在router-view里面展示-->
            <router-view />

          </el-main>

        </el-container>

      </el-container>



</template>

<script>
  import Aside from "@/components/Aside";
  import Header from "@/components/Header";

export default {
  name:'home',
  // components: {
  //   Aside
  // },
  data() {
    return {
      collapseBtnClass:'el-icon-s-fold',
      isCollapse:false,
      sideWidth:200,//自动宽度中的原始宽度
      logoTextShow:true,
    }
  },
components:{//注册组件
    Aside,
    Header,
},
  methods:{
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
      }
  }
}
</script>

<style>
  .headerBg{
 background: #cccccc !important;
    color: lightslategrey;
  }
</style>