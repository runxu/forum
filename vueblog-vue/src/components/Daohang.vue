<template>
    <div class="right">

                    <div class="navbar">
                        <el-menu default-active="1-4-1" class="el-menu-vertical-demo"  :collapse="isCollapse">
                            <el-submenu index="1">
                                <template slot="title">
                                    <i class="el-icon-menu"></i>
                                    <span slot="title">分类</span>
                                </template>
                                <el-menu-item-group v-for="cate in category">
                                    <el-menu-item index="1-1" @click="tocategory(cate.cid)">
                                        {{cate.cname}}
                                    </el-menu-item>
                                </el-menu-item-group>
                            </el-submenu>
                            <el-menu-item index="2">
                                <i class="el-icon-document" @click="toadd"></i>
                                <span slot="title">发表文章</span>
                            </el-menu-item>
                            <el-menu-item index="3" :disabled="!hasLogin">
                                <i class="el-icon-user-solid" @click="touserinfo"></i>
                                <span slot="title">个人中心</span>
                            </el-menu-item>
                            <el-menu-item index="4">
                                <i class="el-icon-refresh-left" @click="refresh"></i>
                                <span slot="title">刷新</span>
                            </el-menu-item>
                        </el-menu>
                    </div>

    </div>
</template>

<script>
    import ElMain from "element-ui/packages/main/src/main";
    import ElContainer from "element-ui/packages/container/src/main";

    export default {
        name: "Daohang",
        components: {
            ElContainer,
            ElMain},
        data(){
            return{

                ownBlog: false,
                clickNum:0,
                isClick:0,
                hasLogin: false,
                text: "",
                userid: '',
                blogs: {},
                category:{},
                isCollapse: true
            }
        },
        methods: {
            Post(){
                this.$router.push("/blog/add")
            },
            getImgUrl(value) {
                return `https://picsum.photos/id/43${value}/1230/500`
            },
            toadd(){
                this.$router.push("/blog/add")
            },
            refresh(){
                location.reload();
            },
            tocategory(cid){
                this.$router.push({name:'Category',params: {categoryid:cid}})
            },
            touserinfo(){
                this.$router.push("/userinfo")
            }
        },
        created() {
            var script = document.createElement('script');
            script.type = 'text/javascript';
            script.src = 'https://widget.qweather.net/standard/static/js/he-standard-common.js?v=2.0';
            document.getElementsByTagName('head')[0].appendChild(script);

            const _this = this
            _this.$axios.get("/lunbo").then(res=>{
                console.log(res)
                _this.category = res.data.data
            })

            const token = _this.$store.getters.getUser.id
            if(token>0){
                _this.hasLogin = true;
            }else {
                _this.hasLogin = false;
            }

        }

    }
</script>

<style scoped>

    .navbar{
        position: fixed;
        right: 0px;
        top: 0px;
    }
</style>