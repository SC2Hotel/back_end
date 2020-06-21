<template>
    <div style="font-size: large">
        <span>酒店名称 : {{currentHotelInfo.name}}</span><br>
        <span>酒店地址 : {{currentHotelInfo.address }}</span><br>
        <span>酒店评分 : {{currentHotelInfo.rate }}</span><br>
        <span>酒店简介 : {{currentHotelInfo.description }}</span><br>
        <span>酒店星级 : {{currentHotelInfo.hotelStar }}</span><br>
        <span>酒店联系方式 : {{currentHotelInfo.phoneNum }}</span><br>
    </div>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from "vuex";

    export default {
        name: 'hotelDetail',
        data() {return{}},
        computed: {
            ...mapGetters([
                'currentHotelInfo',
            ])
        },
        mounted() {
        },
        methods: {
            ...mapMutations([

            ]),
            ...mapActions([

            ])
        }
    }
</script>

<style scoped>

</style>
