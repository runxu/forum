<template>
    <div class="serach">
        <el-menu :default-active="activeIndex" class="myMenu" mode="horizontal">
            <el-menu-item index="1" @click="toIndex" >
                关于本站</el-menu-item>
            <!--            <el-menu-item index="1">首页</el-menu-item>-->
            <el-menu-item index="2" @click="toKen">最新</el-menu-item>
            <el-menu-item index="3" @click="toHot">最热</el-menu-item>
            <el-submenu index="4">
                <template slot="title">类别</template>
                <el-menu-item-group v-for="cate in category">
                <el-menu-item @click="tocategory(cate.cid)">{{cate.cname}}</el-menu-item>
                </el-menu-item-group>
            </el-submenu>
            <el-menu-item >
                <el-input class="border-radius" @keyup.enter.native="search" v-model="keyword">
                    <el-button icon="el-icon-search"  slot="suffix" type="text"  @click="search" ></el-button>
                </el-input>
            </el-menu-item>
        </el-menu>
    </div>
</template>

<script>
    export default {
        name: "navbar",
        data() {
            return {

                keyword:'',
                category:{},
                activeIndex:''

            };
        },
        methods: {
            toIndex(){

                this.$router.push("/about");
            },
            toKen(){
                this.$router.push("/sheet");
            },
            search(){
                const  _this = this;
                this.$router.push({
                    name: 'Search',
                    params: {
                        keyword:_this.keyword
                    }
                });
            },
            toHot(){
                this.$router.push("/hot")
            },
            tocategory(cid){
                this.$router.push({name:'Category',params: {categoryid:cid}})
                location.reload()
            },
        },
        created(){
            this.$axios.get("/lunbo").then(res=>{
                console.log(res)
                this.category = res.data.data
            })
        }
    }
</script>

<style scoped>
    .border-radius{
        width: 220px;
        height: auto;
        margin-bottom: 10px;

    }
    .serach{

    }
    .myMenu{

    }
</style>