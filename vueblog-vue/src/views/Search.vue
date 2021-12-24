<template >
    <div>
        <Header>
        </Header>
        <div class="serach">
            <el-menu :default-active="activeIndex" class="myMenu" mode="horizontal" @select="handleSelect">
                <el-menu-item index="1" @click="toIndex" >
                    关于本站</el-menu-item>
                <!--            <el-menu-item index="1">首页</el-menu-item>-->
                <el-menu-item index="2" @click="toKen">最新</el-menu-item>
                <el-menu-item index="3" @click="toHot">最热</el-menu-item>
                <el-menu-item >
                    <el-input class="border-radius" @keyup.enter.native="search" v-model="keyword">
                        <el-button icon="el-icon-search"  slot="suffix" type="text"  @click="search" ></el-button>
                    </el-input>

                </el-menu-item>
            </el-menu>
        </div>
        <div class="block">

            <el-timeline>
                <el-timeline-item :timestamp="blog.created" placement="top" v-for="blog in blogs">
                    <el-card>
                        <h4>
                            <router-link :to="{name: 'BlogDetail',params:{blogId:blog.id}}">
                                {{blog.title}}
                            </router-link>
                        </h4>
                        <p>
                            {{blog.desription}}
                        </p>

                        <div class="dao">
                            <nav class="level is-mobile">
                                <div class="level-left">
                                    <router-link :to="{name: 'BlogDetail',params:{blogId:blog.id}}">
                      <span class="icon is-small">
                        <van-icon name="like-o" />
                      </span>
                                        {{blog.thumbs}}
                                    </router-link>

                                    <router-link :to="{name: 'BlogDetail',params:{blogId:blog.id}}" class="pl">
                      <span class="icon is-small">
                         <i class="el-icon-chat-line-round"></i>
                      </span>
                                        {{blog.commentnum}}
                                    </router-link>
                                </div>
                            </nav>
                        </div>


                    </el-card>
                </el-timeline-item>
            </el-timeline>

            <el-pagination class="mpage"
                           background
                           layout="prev, pager, next"
                           :current_page = "currentPage"
                           :page-size="pageSize"
                           @current-change=page
                           :total="total">
            </el-pagination>
        </div>
       <Daohang></Daohang>
    </div>

</template>

<script>
    import Header from "../components/Header"
    import ElMenu from "element-ui/packages/menu/src/menu";
    import Navbar from "../components/Navbar"

    import Daohang from "../components/Daohang"
    export default {
        name: "search.vue",
        components: {
            ElMenu,
            Header,
            Navbar,
            Daohang
        },
        props: {
            activeIndex: {
                type: String,
                required: false //在使用组件不一定传值
            }
        },
        data(){
            return {
                blogs: {},
                currentPage: 1,
                total: 0,
                pageSize: 5,
                keyword:'',

            }
        },
        methods: {
            page(currentPage){
                const _this = this;
                _this.$axios.get("/search?currentPage=1&keyword="+_this.keyword).then(res => {
                    console.log(res)
                    _this.blogs = res.data.data.records
                    _this.currentPage = res.data.data.currentPage
                    _this.total = res.data.data.total
                    _this.pageSize = res.data.data.size

                })
            },
            search(){
                const _this = this;
                _this.$axios.get("/search?currentPage=1&keyword="+_this.keyword).then(res => {
                    console.log(res)
                    _this.blogs = res.data.data.records
                    _this.currentPage = res.data.data.currentPage
                    _this.total = res.data.data.total
                    _this.pageSize = res.data.data.size

                })

            },
            toIndex(){
                this.$router.push("/about");
            },
            toKen(){
                this.$router.push("/sheet");
            },

        },
        created(){
            const _this = this
            _this.$axios.get("/search?currentPage="+_this.currentPage+"&keyword="+this.$route.params.keyword).then(res=>{
                console.log(res)
                _this.blogs = res.data.data.records
                _this.currentPage = res.data.data.currentPage
                _this.total = res.data.data.total
                _this.pageSize = res.data.data.size
            })

        }
    }
</script>

<style scoped>
    .mpage{
        margin: 0 auto;
        text-align: center;
    }
    .block{
        margin-top: 15px;
        float: left;
        width: 500px;
        height: auto;
    }

    .border-radius{
        width: 220px;
        height: auto;
        margin-bottom: 10px;

    }
    .serach{

    }
    .serach{

    }
    .myMenu{

    }
    .dao{
        float: right;
    }
    .pl{
        margin-left: 10px;
    }

</style>