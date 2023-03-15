<template >
    <div>
        <Header>
        </Header>
        <Navbar></Navbar>
        <div class="block" :style="{'background-image':formatebackground(this.$route.params.categoryid)}">

            <b-breadcrumb
                    align="is-left"
                    size="is-small"
            >
                <b-breadcrumb-item tag='router-link' to="/">首页</b-breadcrumb-item>
                <b-breadcrumb-item tag='router-link' to="" active>{{cname}}</b-breadcrumb-item>
            </b-breadcrumb>
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

        <div class="tile is-parent">
            <article class="tile is-child box">
                <p class="subtitle">所有分类</p>

                <div class="card">
                    <header class="card-header">
                        <p class="card-header-title">分类名</p>
                        <a href="#" class="card-header-icon" aria-label="more options">
              <span class="icon">
                <i class="fas fa-angle-down" aria-hidden="true"></i>
              </span>
                        </a>
                    </header>
                    <div class="card-content">
                        <div class="content">
                            <section>
                                <b-tooltip
                                        v-for="(cate,index) in category"
                                        :key="index"
                                        :label="cate.cdesription"
                                        class="is-info"
                                >
                                    <button
                                            class="button is-info is-outlined"
                                            @click="entercateid(cate.cid)"
                                    >{{cate.cname}}</button>
                                </b-tooltip>
                            </section>
                            <br />
                        </div>
                    </div>
                    <footer class="card-footer">
                        <a href="#" class="card-footer-item">点击你感兴趣的板块</a>
                    </footer>
                </div>
            </article>
        </div>
        <Daohang></Daohang>
    </div>
</template>

<script>
    import Header from "../components/Header"
    import ElMenu from "element-ui/packages/menu/src/menu";
    import Navbar from "../components/Navbar"
    import Right from "../components/Right"
    import Daohang from "../components/Daohang"

    export default {
        name: "category.vue",
        components: {
            ElMenu,
            Header,
            Navbar,
            Right,
            Daohang
        },
        props: {
            activeIndex: {
                type: String,
                required: false,//在使用组件不一定传值
                activeIndex: '1',
            }
        },
        data(){
            return {
                blogs: {},
                currentPage: 1,
                total: 0,
                pageSize: 4,
                keyword:'',
                category:{},
                cname:'',
            }
        },
        methods: {
            page(currentPage){
                const _this = this
                console.log(this.$route.params.categoryid)
                _this.$axios.get("/category?currentPage="+currentPage+"&categoryid="+this.$route.params.categoryid).then(res=>{
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
                const _this = this

                _this.$axios.get("/blogs?currentPage=1"+"&userId=-1").then(res=>{
                    console.log(res)
                    _this.blogs = res.data.data.records
                    _this.currentPage = res.data.data.currentPage
                    _this.total = res.data.data.total
                    _this.pageSize = res.data.data.size
                })
            },
            toKen(){
                const _this = this

                _this.$axios.get("/blogs?currentPage=1"+"&userId=-1").then(res=>{
                    console.log(res)
                    _this.blogs = res.data.data.records
                    _this.currentPage = res.data.data.currentPage
                    _this.total = res.data.data.total
                    _this.pageSize = res.data.data.size
                })
            },
            entercateid(cid){
                this.$router.push({name:'Category',params: {categoryid:cid}})
                location.reload();

            },
            formatebackground(val){

                if(val == 1){

                }
            }

        },
        created(){
            this.page(1)
            const _this = this
            _this.$axios.get("/lunbo").then(res=>{
                console.log(res)
                _this.category = res.data.data
            })

            _this.$axios.get("/breadxie?cid="+this.$route.params.categoryid).then(res=>{
                console.log(res)
                _this.cname = res.data.data.cname
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
        width: 600px;
        height: auto;


    }
    .dao{
        float: right;
    }


</style>