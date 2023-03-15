<template >
    <div>
        <Header>
        </Header>
        <Navbar></Navbar>

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
                        <svg
                                xmlns="http://www.w3.org/2000/svg"
                                width="16"
                                height="16"
                                fill="currentColor"
                                class="bi bi-hand-thumbs-up"
                                viewBox="0 0 16 16"
                        >
            <path
                    fill-rule="evenodd"
                    d="M6.956 1.745C7.021.81 7.908.087 8.864.325l.261.066c.463.116.874.456 1.012.965.22.816.533 2.511.062 4.51a9.84 9.84 0 0 1 .443-.051c.713-.065 1.669-.072 2.516.21.518.173.994.681 1.2 1.273.184.532.16 1.162-.234 1.733.058.119.103.242.138.363.077.27.113.567.113.856 0 .289-.036.586-.113.856-.039.135-.09.273-.16.404.169.387.107.819-.003 1.148a3.163 3.163 0 0 1-.488.901c.054.152.076.312.076.465 0 .305-.089.625-.253.912C13.1 15.522 12.437 16 11.5 16v-1c.563 0 .901-.272 1.066-.56a.865.865 0 0 0 .121-.416c0-.12-.035-.165-.04-.17l-.354-.354.353-.354c.202-.201.407-.511.505-.804.104-.312.043-.441-.005-.488l-.353-.354.353-.354c.043-.042.105-.14.154-.315.048-.167.075-.37.075-.581 0-.211-.027-.414-.075-.581-.05-.174-.111-.273-.154-.315L12.793 9l.353-.354c.353-.352.373-.713.267-1.02-.122-.35-.396-.593-.571-.652-.653-.217-1.447-.224-2.11-.164a8.907 8.907 0 0 0-1.094.171l-.014.003-.003.001a.5.5 0 0 1-.595-.643 8.34 8.34 0 0 0 .145-4.726c-.03-.111-.128-.215-.288-.255l-.262-.065c-.306-.077-.642.156-.667.518-.075 1.082-.239 2.15-.482 2.85-.174.502-.603 1.268-1.238 1.977-.637.712-1.519 1.41-2.614 1.708-.394.108-.62.396-.62.65v4.002c0 .26.22.515.553.55 1.293.137 1.936.53 2.491.868l.04.025c.27.164.495.296.776.393.277.095.63.163 1.14.163h3.5v1H8c-.605 0-1.07-.081-1.466-.218a4.82 4.82 0 0 1-.97-.484l-.048-.03c-.504-.307-.999-.609-2.068-.722C2.682 14.464 2 13.846 2 13V9c0-.85.685-1.432 1.357-1.615.849-.232 1.574-.787 2.132-1.41.56-.627.914-1.28 1.039-1.639.199-.575.356-1.539.428-2.59z"
            />
          </svg>
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


        <Right></Right><Daohang></Daohang>

        <Footer></Footer>
    </div>

</template>

<script>
    import Header from "../components/Header"
    import ElMenu from "element-ui/packages/menu/src/menu";
    import Navbar from "../components/Navbar"
    import ElImage from "element-ui/packages/image/src/main";
    import Footer from "../components/Footer"
    import ElLink from "element-ui/packages/link/src/main";
    import Right from "../components/Right"
    import Daohang from "../components/Daohang"

    export default {
        name: "sheet.vue",
        components: {
            ElLink,
            ElImage,
            ElMenu,
            Header,
            Navbar,
            Footer,
            Right,
            Daohang
        },
        props: {
            activeIndex: {
                type: String,
                default: '2',
                required: false //在使用组件不一定传值
            }
        },
        data(){
            return {
                blogs: {},
                gonggao: {},
                currentPage: 1,
                total: 0,
                pageSize: 5,
                keyword:'',

            }
        },
        methods: {
            page(currentPage){
                const _this = this
                _this.$axios.get("/sheet?currentPage="+currentPage).then(res=>{
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


        },
        created(){

            this.page(1)




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
    .pl{
        margin-left: 10px;
    }







</style>