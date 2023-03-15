<template >
    <div>
        <Header></Header>
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
    </div>
</template>

<script>
    import Header from "../components/Header"
    export default {
        name: "Blog.vue",
        components: {Header},
        data(){

            return {

                userid: this.$store.getters.getUser.id,
                blogs: {},
                currentPage: 1,
                total: 0,
                pageSize: 4,

            }
        },
        methods: {
            page(currentPage){
                const _this = this

                _this.$axios.get("/blogs?currentPage="+currentPage+"&userId="+this.$store.getters.getUser.id).then(res=>{
                    console.log(res)
                    _this.blogs = res.data.data.records
                    _this.currentPage = res.data.data.currentPage
                    _this.total = res.data.data.total
                    _this.pageSize = res.data.data.size
                })
            }
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
        margin-top: 30px;
    }
    .navInfo{
        float: right;
        flex: auto;
    }
    .myMenu{
        width: 100%;
    }

    em{
        color: red;
    }
</style>