<template>
    <div>
    <nav class="navbar" role="navigation" aria-label="main navigation">
        <div class="navbar-brand">
            <a class="navbar-item" href="https://github.com/runxu/forum">
                <img src="@/assets/images/logo.png" width="112" height="28" />
            </a>

        </div>

        <div id="navbarBasicExample" class="navbar-menu">
            <div class="navbar-start">
                <a class="navbar-item" href="/sheet">
                    首页
                </a>

                <a class="navbar-item" href="/blog/add">发表文章</a>

                <div class="navbar-item has-dropdown is-hoverable">
                    <a class="navbar-link">More</a>

                    <div class="navbar-dropdown">
                        <a class="navbar-item" href="/about">About</a>
                        <a class="navbar-item" href="/hot">Hot</a>
                    </div>

                </div>
            </div>

            <div class="navbar-end">
                <div class="navbar-item">
                    <div class="buttons">
                        <a class="button is-primary" href="/login">
                            <strong>Sign up</strong>
                        </a>
                        <a class="button is-link" href="/register" v-show="!hasLogin"><strong>Regiter up</strong></a>
                        <a class="button is-success" href="/userinfo" v-show="hasLogin">{{user.username}}的个人中心</a>
                        <span v-show="hasLogin" class="button is-light" @click="logout">退出</span>

                    </div>

                </div>
            </div>
        </div>

    </nav>
    </div>
</template>


<script>
    import ElLink from "element-ui/packages/link/src/main";

    export default {
        components: {ElLink},
        name: "Headerr",
        data(){
            return{
                user: {
                    username: '',

                },
                hasLogin:''

            }
        },
        methods: {
            logout() {
                const _this = this
                _this.$axios.get("/logout", {
                    headers: {
                        "Authorization": localStorage.getItem("token")
                    }
                }).then(res => {
                    _this.$store.commit("REMOVE_INFO")
                    _this.$router.push("/login")
                })
            },
        },
        created(){
            if(this.$store.getters.getUser.username){
                this.user.username = this.$store.getters.getUser.username
                this.user.avatar = this.$store.getters.getUser.avatar
                this.hasLogin = true
                this.user.id = this.$store.getters.getUser.id

            }
        }
    }
</script>

<style scoped>


</style>