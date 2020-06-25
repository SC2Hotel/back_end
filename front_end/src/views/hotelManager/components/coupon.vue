<template>
    <div>
        <a-modal
                :visible="couponVisible"
                title="优惠策略"
                width="900px"
                :footer="null"
                @cancel="cancel"
        >
            <div style="text-align:right">
                <a-button type="primary" @click="addCoupon">+添加优惠策略</a-button>
            </div>
            <!-- 这里是模态框内容区域，请编写列表代码与添加策略按钮 -->
            <a-table :columns="columns" :dataSource="couponList">
                <a-tag color="red" slot="couponName" slot-scope="coupon">
                    {{ coupon }}
                </a-tag>
            </a-table>
        </a-modal>
        <AddCoupon></AddCoupon>
    </div>
</template>
<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import AddCoupon from './addCoupon'

    const columns = [
        // 这里定义列表头
        {
            key:1,
            scopedSlots: { customRender: 'couponName' },
            title: '优惠类型',
            dataIndex: 'couponName',
        },
        {
            key:2,
            title: '折扣',
            dataIndex: 'discount',
        },
        {
            key:3,
            title: '优惠介绍',
            dataIndex: 'description',
        },
        {
            key:4,
            title: '优惠金额',
            dataIndex: 'discountMoney',
        }
    ];

    export default {
        name: 'coupon',
        data() {
            return {
                columns,
            }
        },
        components: {
            AddCoupon,
        },
        computed: {
            ...mapGetters([
                'couponVisible',
                'couponList',
            ])
        },
        methods: {
            ...mapMutations([
                'set_addCouponVisible',
                'set_couponVisible',
            ]),
            cancel() {
                this.set_couponVisible(false)
            },
            addCoupon() {
                this.set_addCouponVisible(true),
                    this.set_couponVisible(false)
            },
        },
    }
</script>
<style scoped>

</style>
