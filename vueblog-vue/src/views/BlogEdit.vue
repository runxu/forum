<template>
    <div>

        <Header></Header>
        <div class="m-content">



            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">

                <el-form-item label="分类" prop="category">
                    <el-select v-model="ruleForm.categoryid" placeholder="请选择分类" class="category">
                        <el-option label="学习" value="1"></el-option>
                        <el-option label="篮球" value="2"></el-option>
                        <el-option label="足球" value="3"></el-option>
                        <el-option label="杂谈" value="4"></el-option>
                        <el-option label="其他" value="5"></el-option>
                    </el-select>
                </el-form-item>


                <el-form-item label="标题" prop="title">
                    <el-input v-model="ruleForm.title"></el-input>
                </el-form-item>



                <el-form-item label="摘要" prop="desription">
                    <el-input type="textarea" v-model="ruleForm.desription"></el-input>
                </el-form-item>

                <el-form-item label="内容" prop="content">
                   <mavon-editor v-model="ruleForm.content" ref=md @imgAdd="imgAdd" @imgDel="imgDel"></mavon-editor>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
                    <el-button @click="resetForm('ruleForm')">重置</el-button>
                </el-form-item>
            </el-form>
        </div>

    </div>
</template>

<script>
    import Header from "../components/Header"
    export default {
        name: "blog-edit",
        components:{Header},
        data() {
            return {
                ruleForm: {
                    id: '',
                    title: '',
                    desription:'',
                    content: '',
                    thumbs: '',
                    categoryid: '',
                },
                rules: {
                    title: [
                        { required: true, message: '请输入标题', trigger: 'blur' },
                        { min: 3, max: 25, message: '长度在 3 到 25 个字符', trigger: 'blur' }
                    ],
                    desription: [
                        { required: true, message: '请输入摘要', trigger: 'blur' }
                    ],
                    content: [
                        {  required: true, message:'请输入内容', trigger: 'blur' }
                    ],
                    categoryid: [
                        { required: true, message: '请选择分类', trigger: 'change' }
                    ],
                }
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        const _this = this
                        _this.ruleForm.content = _this.$refs.md.d_value;
                        this.$axios.post('/blog/edit', this.ruleForm, {
                            headers: {
                                "Authorization": localStorage.getItem("token")
                            }
                        }).then(res => {
                            this.$alert('操作成功', '提示', {
                                confirmButtonText: '确定',
                                callback: action => {
                                    _this.$router.push("/blogs")
                                }
                            });
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            imgAdd(pos, $file) {
                var _this = this
                var formdata = new FormData();
                formdata.append('image', $file);
                this.$axios.post("/upload",formdata,{
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }).then((response) => {
                    // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
                    if (response.status === 200) {
                        var url = response.data.data;
                        _this.$refs.md.$img2Url(pos,url)
                    }
                })
            },
            imgDel(pos){
                delete this.img_file[pos];
            }
            },
            created() {
                const blogId = this.$route.params.blogId
                console.log(blogId)
                const _this = this
                if(blogId) {
                    this.$axios.get('/blog/' + blogId).then(res => {
                        const blog = res.data.data
                        _this.ruleForm.id = blog.id
                        _this.ruleForm.title = blog.title
                        _this.ruleForm.desription = blog.desription
                        _this.ruleForm.content = blog.content
                    })
                }


            }

    }
</script>

<style scoped>
    .m-content{
        text-align: center;
        margin-top: 20px;
    }
    .category{
        width: 860px;
    }

</style>