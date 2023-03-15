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
  </div>
</template>

<script>
    import Header from "../components/Header"
    import ElMenu from "element-ui/packages/menu/src/menu";
    import Navbar from "../components/Navbar"
    import Right from "../components/Right"
    import Daohang from "../components/Daohang"

    export default {
        name: "about.vue",
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

            }
        },
        methods: {
            page(currentPage){
                const _this = this
                _this.$axios.get("/blogs?currentPage="+currentPage+"&userId=-1").then(res=>{
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
    margin-top: 15px;
    float: left;
    width: 600px;
    height: auto;
  }

</style>