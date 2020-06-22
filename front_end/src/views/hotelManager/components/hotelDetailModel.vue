<template>
    <a-modal
            :visible="hotelDetailModalVisible"
            title="酒店详情"
            :footer="null"
            @cancel="cancel"
    >
        <div style="font-size: large">
            <span>酒店名称 : {{currentHotelInfo.name}}</span><br>
            <a-input
                    :default-value="currentHotelInfo.address"
                    v-model="currentHotelInfo.address"
                    placeholder="请填写酒店地址"
                    v-if="modify"
                    addon-before="酒店地址"
            />
            <span v-else>酒店地址 : {{currentHotelInfo.address }}</span><br>
            <a-input
                    :default-value="currentHotelInfo.description"
                    placeholder="请填写酒店简介"
                    v-if="modify"
                    v-model="currentHotelInfo.description"
                    addon-before="酒店简介"
            />
            <span v-else>酒店简介 : {{currentHotelInfo.description}}</span><br>
            <a-input
                    :default-value="currentHotelInfo.phoneNum"
                    placeholder="请填写酒店联系方式"
                    v-if="modify"
                    v-model="currentHotelInfo.phoneNum"
                    addon-before="酒店联系方式"
            />
            <span v-else>酒店联系方式 : {{currentHotelInfo.phoneNum }}</span><br>
            <span>酒店评分 : {{currentHotelInfo.rate }}</span><br>
            <span>酒店星级 : {{currentHotelInfo.hotelStar }}</span><br>
        </div>
        <div style="display: flex;align-items: center;justify-content: center">
        <a-button @click="changeModify">{{modify?'确定':'修改'}}</a-button>
        </div>
    </a-modal>
</template>

<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'

    export default {
        name: "hotelDetailModel",
        data() {
            return {
                modify:false,
                tmpHotelInfo:{
                    phoneNumber:'',
                }
            }
        },
        components: {

        },
        computed: {
            ...mapGetters([
                'hotelDetailModalVisible',
                'currentHotelInfo',
            ]),
        },
        methods: {
            ...mapMutations([
                'set_hotelDetailModalVisible',
            ]),
            ...mapActions([
                'updateHotelDetail',
            ]),
            cancel(){
                this.set_hotelDetailModalVisible(false)
            },
            changeModify(){
                if(this.modify){
                    let tmpInfo={
                        id:this.currentHotelInfo.id,
                        address:this.currentHotelInfo.address,
                        bizRegion:this.currentHotelInfo.bizRegion,
                        hotelStar:this.currentHotelInfo.hotelStar,
                        description:this.currentHotelInfo.description,
                    }
                    this.updateHotelDetail(tmpInfo)
                }
                this.modify=!this.modify
            }
        }
    }
</script>

<style scoped>

</style>
