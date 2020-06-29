<template>
    <a-modal
            :visible="creditLogModalVisible"
            title="信用值变化记录"
            @cancel="closeCreditLogModal"
            :footer="null"
            width="900px"
    >
        <a-table
                :columns="columns"
                :dataSource="creditLog">
        </a-table>
    </a-modal>
</template>

<script>
    import {mapGetters, mapActions, mapMutations} from 'vuex'

    import moment from 'moment'

    const formatterTime = (val) => {
        return val ? moment(val).format('YYYY-MM-DD HH:mm:ss'): ''
    }

    const columns = [
        {
            title: '订单号',
            dataIndex: 'orderId'
        },
        {
            title: '时间',
            dataIndex: 'time',
            customRender : formatterTime
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
                formatterTime
            }
        },
        computed: {
            ...mapGetters([
                'creditLogModalVisible',
                'creditLog'
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
            },
        }
    }
</script>

<style scoped>

</style>