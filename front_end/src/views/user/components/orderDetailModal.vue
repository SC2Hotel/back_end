<template>
    <a-modal
    :visible="orderDetailModalVisible"
    title="订单详情"
    cancelText="取消"
    okText="确认"
    @cancel="cancelOrder"
    @ok="cancelOrder"
    >
        <p>123</p>
    </a-modal>
</template>

<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'

    export default {
        name: "orderDetailModal",
        data(){
            return {}
        },
        computed: {
            ...mapGetters([
                'orderDetailModalVisible',
            ]),
        },
        methods: {
            ...mapMutations([
                'set_orderDetailModalVisible'
            ]),
            ...mapActions([
            ]),
            cancelOrder() {
                this.set_orderDetailModalVisible(false)
            },
        },

    }


</script>