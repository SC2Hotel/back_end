<template>
    <a-modal
        :visible="orderDetailModalVisible"
        title="订单详情"
        :footer="null"
        @cancel="cancelOrderDetail"
        @ok="cancelOrderDetail"
    >
        <div style="font-size: large">
            <span style="display: flex;justify-content: space-between">
                <span>预定时间 : {{currentOrder.createDate}}</span>
                <span>订单状态 : {{currentOrder.orderState}}</span>
            </span><br>
            <span>酒店名称 : {{currentOrder.hotelName}}</span><br>
            <span>预定入住时间 : {{currentOrder.checkInDate}}</span><br>
            <span>预定离开时间 : {{currentOrder.checkOutDate}}</span><br><br>
            <span>用户名 : {{currentOrder.clientName}}</span><br>
            <span>预定入住人数 : {{currentOrder.peopleNum}}</span><br>
            <span>预定人电话: {{currentOrder.phoneNumber}}</span><br>
            <span>预定房间类型 : {{currentOrder.roomType=="Family"?"家庭房":currentOrder.roomType=="BigBed"?"大床房":"双床房"}}</span><br>
            <span>预定房间数目 : {{currentOrder.roomNum}}</span><br>
            <span>是否有儿童 : {{currentOrder.haveChild?"是":"否"}}</span><br>
            <span>预定总价 : ¥{{currentOrder.price}}</span><br>

        </div>
    </a-modal>
</template>

<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'

    export default {
        name: "orderDetailModal",
        data() {
            return {}
        },
        computed: {
            ...mapGetters([
                'orderDetailModalVisible',
                'currentOrder'
            ]),
        },
        methods: {
            ...mapMutations([
                'set_orderDetailModalVisible'
            ]),
            cancelOrderDetail() {
                this.set_orderDetailModalVisible(false)
            },
        },

    }
</script>
