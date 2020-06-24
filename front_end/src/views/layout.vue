<template>
    <div id="layout">
        <Header></Header>
        <transition name="fade-transform" mode="out-in">
            <router-view/>
        </transition>
    </div>
</template>
<script>
import Header from '@/components/header'
import {mapActions} from "vuex";
export default {
    name: 'layout',
    components: {
        Header
    },
    async mounted() {
        await this.getUserInfo()
    },
    methods:{
        ...mapActions([
            'getUserInfo',
        ]),
    },
}
</script>
