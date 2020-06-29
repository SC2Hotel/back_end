<template>
    <a-modal
            :visible="creditLogModalVisible"
            title="信用值变化记录"
            @cancel="closeCreditLogModal"
            :footer="null"
    >
        <a-table
                :columns="columns"></a-table>
    </a-modal>
</template>

<script>
    import {mapGetters, mapActions, mapMutations} from 'vuex'

    const columns = [
        {
            title: '订单号',
            dataIndex: 'orderId'
        },
        {
            title: '时间',
            dataIndex: 'time'
        },
        {
            title: '原因',
            dataIndex: 'reason'
        },
        {
            title: '信用值',
            dataIndex: 'credit'
        },
        {
            title: '信用值变化',
            dataIndex: 'changeNum'
        }
    ]

    export default {
        name: "creditLogModal",
        data() {
            return {
                columns,
            }
        },
        computed: {
            ...mapGetters([
                'creditLogModalVisible'
            ])
        },
        methods: {
            ...mapMutations([
                'set_creditLogModalVisible'
            ]),
            ...mapActions([
            ]),
            closeCreditLogModal() {
                this.set_creditLogModalVisible(false)
            }
        }
    }
</script>

<style scoped>

</style>