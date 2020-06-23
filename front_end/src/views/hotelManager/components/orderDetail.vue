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
                <span style="display: flex;flex-direction: column;justify-content: center">
                    <span>订单状态 : {{currentOrder.orderState}}</span>
                    <span v-if="currentOrder.orderState==='已预订'"><a-button type="primary" size="small" @click="executeOrder">执行订单</a-button></span>
                    <span v-if="currentOrder.orderState==='异常'"><a-button type="danger" size="small" @click="cancelOrder">处理订单</a-button></span>
                </span>
            </span>
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
            <span v-if="currentOrder.orderState==='已评价'">
                <span>
                    评分 : {{commentItem.score}}
                </span>
                <br>
                <span>
                    评价 : {{commentItem.content}}
                </span>
            </span>
        </div>
    </a-modal>
</template>

<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'

    export default {
        name: "orderDetailModal",
        data() {
            return {
            }
        },
        computed: {
            ...mapGetters([
                'orderDetailModalVisible',
                'currentOrder',
                'userId',
                'commentItem',
                'currentHotelInfo'
            ]),
        },
        methods: {
            ...mapActions([
                'updateExecuteOrder',
                'delayCheckInOrder',
                'getAllOrders',
                'getOrderByHotel'
            ]),
            ...mapMutations([
                'set_orderDetailModalVisible',
                'set_currentOrder'
            ]),
            async cancelOrderDetail() {
                this.set_orderDetailModalVisible(false)
                console.log(this.commentItem)
                await this.this.getOrderByHotel(this.currentHotelInfo.id)
            },
            //处理订单
            executeOrder(){
                this.updateExecuteOrder({orderId:this.currentOrder.id,userId:this.userId})
                this.set_currentOrder({...this.currentOrder,orderState:'已执行'})
            },
            //延时撤回订单
            async cancelOrder(){
                const data = await this.delayCheckInOrder({orderId:this.currentOrder.id})
                if(data==="超过最长延迟入住时间"){
                    this.set_currentOrder({...this.currentOrder,orderState:'超过最长延迟入住时间'})
                }else{
                    this.set_currentOrder({...this.currentOrder,orderState:'延长成功'})
                }

            }
        },

    }
</script>
