<template>
    <div class="manageHotel-wrapper">
        <a-tabs>
            <a-tab-pane tab="酒店管理" key="1">
                <a-form :form="form">
                    <a-form-item label="酒店名" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <span>{{hotelList.name}}</span>
                    </a-form-item>
                    <a-form-item label="商圈" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <a-select placeholder="请选择酒店商圈"
                                  v-decorator="['hotelBizRegion', { rules: [{ required: true, message: '请输入酒店商圈' }] }]"
                                  v-if="modify" @change="onChange">
                            <a-select-option v-for="item in bizRegions" :key="item">
                                {{item}}
                            </a-select-option>
                        </a-select>
                        <span v-else>{{hotelList.bizRegion}}</span>
                    </a-form-item>
                    <a-form-item label="地址" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <a-input placeholder="请填写酒店地址"
                                 v-decorator="['hotelAddress', { rules: [{ required: true, message: '请输入酒店地址' }] }]"
                                 v-if="modify"></a-input>
                        <span v-else>{{hotelList.address}}</span>
                    </a-form-item>
                    <a-form-item :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }" label="简介">
                        <a-input placeholder="请填写酒店简介"
                                 v-decorator="['hotelDescription', { rules: [{ required: true, message: '请输入酒店地址' }] }]"
                                 v-if="modify"></a-input>
                        <span v-else> {{hotelList.description}}</span>
                    </a-form-item>
                    <a-form-item :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }" label="星级">
                        <a-select placeholder="请填写酒店星级"
                                  v-decorator="['hotelStar', { rules: [{ required: true, message: '请输入酒店星级' }] }]"
                                  v-if="modify">
                            <a-select-option value="One">
                                One
                            </a-select-option>
                            <a-select-option value="Two">
                                Two
                            </a-select-option>
                            <a-select-option value="Three">
                                Three
                            </a-select-option>
                            <a-select-option value="Four">
                                Four
                            </a-select-option>
                            <a-select-option value="Five">
                                Five
                            </a-select-option>
                        </a-select>
                        <a-rate v-else :default-value="hotelList.rate" disabled/>
                    </a-form-item>
                    <a-form-item :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }" label="评价">
                        <span>{{hotelList.rate}}</span>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify">
                        <a-button type="primary" @click="saveModify">
                            保存
                        </a-button>
                        <a-button type="default" style="margin-left: 30px" @click="cancelModify">
                            取消
                        </a-button>
                    </a-form-item>
                    <a-form-item v-else>
                        <a-button type="primary" @click="modifyInfo" style="margin: 20px">
                            修改信息
                        </a-button>
                        <a-button type="primary" @click="addRoom(hotelList.id)" style="margin: 20px">录入房间</a-button>
                        <a-button type="info" @click="showCoupon(hotelList.id)" style="margin: 20px">优惠策略</a-button>
                    </a-form-item>
                </a-form>
            </a-tab-pane>
            <a-tab-pane tab="订单管理" key="2">
                <a-table
                        :columns="columns2"
                        :dataSource="orderList"
                        bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="roomType" slot-scope="text">
                        <span v-if="text == 'BigBed'">大床房</span>
                        <span v-if="text == 'DoubleBed'">双床房</span>
                        <span v-if="text == 'Family'">家庭房</span>
                    </span>
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="showOrderDetail(record)">订单详情</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-popconfirm
                                title="确定想删除该订单吗？"
                                @confirm="deleteOrder(record)"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger" size="small">删除订单</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="所有评价" key="3">

            </a-tab-pane>
        </a-tabs>
        <AddRoomModal></AddRoomModal>
        <Coupon></Coupon>
        <OrderDetailModal></OrderDetailModal>
    </div>
</template>
<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import AddRoomModal from './components/addRoomModal'
    import Coupon from './components/coupon'
    import orderDetail from './components/orderDetail'
    import OrderDetailModal from "./components/orderDetail";

    const moment = require('moment')
    const columns2 = [
        {
            title: '订单号',
            dataIndex: 'id',
        },
        {
            title: '用户名',
            dataIndex: 'clientName'
        },
        {
            title: '酒店名',
            dataIndex: 'hotelName',
        },
        {
            title: '房型',
            dataIndex: 'roomType',
            scopedSlots: {customRender: 'roomType'}
        },
        {
            title: '入住时间',
            dataIndex: 'checkInDate',
            scopedSlots: {customRender: 'checkInDate'}
        },
        {
            title: '离店时间',
            dataIndex: 'checkOutDate',
            scopedSlots: {customRender: 'checkOutDate'}
        },
        {
            title: '入住人数',
            dataIndex: 'peopleNum',
        },
        {
            title: '房价',
            dataIndex: 'price',
        },
        {
            title:'状态',
            dataIndex: 'orderState'
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },
    ];
    export default {
        name: 'manageHotel',
        data() {
            return {
                modify: false,
                formLayout: 'horizontal',
                pagination: {},
                columns2,
                form: this.$form.createForm(this, {name: 'manageHotel'}),
                selected: "西单"
                // hotelStar: this.hotelList.hotelStar=='Five'?5:this.hotelList.hotelStar=='Four'?4:this.hotelList.hotelStar=='Three'?3:this.hotelList.hotelStar=='Two'?2:1
            }
        },
        components: {
            OrderDetailModal,
            // AddHotelModal,
            AddRoomModal,
            Coupon,
            // orderDetail,
            // HotailDetailModel,
        },
        computed: {
            ...mapGetters([
                'bizRegions',
                'orderList',
                'hotelList',
                'addRoomModalVisible',
                'activeHotelId',
                'couponVisible',
                'currentHotelInfo',
                'userInfo'
            ]),
        },
        async mounted() {
            await this.getUserInfo()
            await this.getHotelByManager(this.userInfo.id)
            await this.getBizregions()
            await this.getOrderByHotel(this.hotelList.id)
            await this.getHotelComment(this.hotelList.id)
        },
        methods: {
            ...mapMutations([
                'set_addHotelModalVisible',
                'set_addRoomModalVisible',
                'set_couponVisible',
                'set_activeHotelId',
                'set_orderDetailModalVisible',
                'set_currentOrder',
                'set_currentHotelId',
                'set_hotelDetailModalVisible',
            ]),
            ...mapActions([
                'updateHotelDetail',
                'getBizregions',
                'getHotelByManager',
                'getHotelCoupon',
                'delOrder',
                'delHotel',
                'getUserInfo',
                'getOrderByHotel',
                'getHotelComment'
            ]),
            addHotel() {
                this.set_addHotelModalVisible(true)
            },
            addRoom(record) {
                this.set_activeHotelId(record)
                this.set_addRoomModalVisible(true)
            },
            showCoupon(record) {
                this.set_activeHotelId(record)
                this.set_couponVisible(true)
                this.getHotelCoupon()
            },
            deleteOrder(record) {
                this.delOrder({orderId: record.id})
            },
            showOrderDetail(record) {
                this.set_currentOrder(record)
                this.set_orderDetailModalVisible(true)
            },
            modifyInfo() {
                setTimeout(() => {
                    this.form.setFieldsValue({
                        'hotelDescription': this.hotelList.description,
                        'hotelStar': this.hotelList.hotelStar,
                        'hotelAddress': this.hotelList.address,
                    })
                }, 0)
                this.modify = true
            },
            cancelModify() {
                this.modify = false
            },
            saveModify() {
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            description: this.form.getFieldValue('hotelDescription'),
                            hotelStar: this.form.getFieldValue('hotelStar'),
                            address: this.form.getFieldValue('hotelAddress'),
                            bizRegion: this.selected,
                            id: this.hotelList.id
                        }
                        console.log(data)
                        this.updateHotelDetail(data).then(() => {
                            this.getHotelByManager(this.userInfo.id)
                            this.modify = false
                        })
                    }
                });
            },
            onChange(value) {
                this.selected = value
            },
        }
    }
</script>
<style scoped lang="less">
    .manageHotel-wrapper {
        padding: 50px;

        .chart {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px
        }
    }
</style>
<style lang="less">
    .manageHotel-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">

</style>
