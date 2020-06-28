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
                <!--房间列表-->
                <a-table
                        :columns="columns4"
                        :dataSource="currentHotelInfo.rooms"
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="action" slot-scope="record">
                        <a-button type="danger" @click="delRoom(record)">删除房间</a-button>
                    </span>
                </a-table>
                <!--end-->
            </a-tab-pane>
            <a-tab-pane tab="订单管理" key="2">
                <a-input-search placeholder="请输入订单号、用户名或酒店名" enter-button @search="onSearch" style="width: 35%"
                                v-model="searchContent"/>
                <a-select style="width: 20%;margin-left: 10px" @change="handleChange" placeholder="订单状态" v-model="orderState">
                    <a-select-option value="全部">
                        全部
                    </a-select-option>
                    <a-select-option value="已预订">
                        已预订
                    </a-select-option>
                    <a-select-option value="已执行">
                        已执行
                    </a-select-option>
                    <a-select-option value="异常">
                        异常
                    </a-select-option>
                </a-select>
                <a-table
                        :columns="columns2"
                        :dataSource="showOrderList"
                        bordered
                        style="margin-top: 10px"
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="roomType" slot-scope="text">
                        <span v-if="text == 'BigBed'">大床房</span>
                        <span v-if="text == 'DoubleBed'">双床房</span>
                        <span v-if="text == 'Family'">家庭房</span>
                    </span>
                    <a-tag slot="orderState" :color=" text === '已预订' ? 'blue': text === '异常' ? 'red' : text === '已执行' ? 'green' : text === '已撤销' ? 'orange' : text==='超过最迟延时入住期限' ? 'grey':'' " slot-scope="text">
                        {{ text }}
                    </a-tag>
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
                <a-table
                        :columns="columns1"
                        :dataSource="hotelCommentList"
                        bordered
                >
                </a-table>
            </a-tab-pane>
        </a-tabs>
        <AddRoomModal></AddRoomModal>
        <Coupon></Coupon>
        <order-detail></order-detail>
    </div>
</template>
<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import AddRoomModal from './components/addRoomModal'
    import Coupon from './components/coupon'
    import orderDetail from './components/orderDetail'

    const moment = require('moment')
    const columns1 = [
        {
            title: '评分',
            dataIndex: 'score'
        },
        {
            title: '评价',
            dataIndex: 'content'
        }
    ]
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
            title: '状态',
            filters: [{text: '已预订', value: '已预订'}, {text: '已撤销', value: '已撤销'}, {text: '已执行', value: '已执行'},{text:'异常',value:'异常'},{text:'超过最迟延时入住期限',value:'超过最迟延时入住期限'}],
            onFilter: (value, record) => record.orderState.includes(value),
            dataIndex: 'orderState',
            scopedSlots: {customRender: 'orderState'}
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },
    ]
    const columns3 = [
        {
            title: '房型',
            dataIndex: 'roomType',
            key: 'roomType',
        },
        {
            title: '今日剩余房间数',
            dataIndex: 'curNum',
            key: 'curNum',
        },
        {
            title: '总房间数',
            dataIndex: 'total',
            key: 'total',
        },
        {
            title: '入住人数',
            key: 'peopleNum',
            dataIndex: 'peopleNum',
        },
        {
            title: '房价',
            key: 'price',
            dataIndex: 'price',
            scopedSlots: {customRender: 'price'}
        },
        {
            title: 'Action',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },
    ];
    const columns4 = [
        {
            title: '房间类型',
            dataIndex: 'roomType'
        },
        {
            title: '房间总数',
            dataIndex: 'total'
        },
        {
            title: '房间剩余数',
            dataIndex: 'curNum'
        },
        {
            title: '房间价格',
            dataIndex: 'price'
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },
    ]
    export default {
        name: 'manageHotel',
        data() {
            return {
                modify: false,
                formLayout: 'horizontal',
                pagination: {},
                columns2,
                columns4,
                columns1,
                columns3,
                form: this.$form.createForm(this, {name: 'manageHotel'}),
                selected: "西单",
                // hotelStar: this.hotelList.hotelStar=='Five'?5:this.hotelList.hotelStar=='Four'?4:this.hotelList.hotelStar=='Three'?3:this.hotelList.hotelStar=='Two'?2:1
                searchContent: "",//搜索的内容
                showOrderList: [],//筛选后的订单列表
                orderState:'',//要筛选出的订单状态
            }
        },
        components: {
            // AddHotelModal,
            AddRoomModal,
            Coupon,
            orderDetail,
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
                'userInfo',
                'hotelCommentList'
            ]),
        },
        async mounted() {
            await this.getUserInfo()
            await this.getHotelByManager(this.userInfo.id)
            await this.getBizregions()
            if (!this.hotelList.id) return;
            await this.set_currentHotelId(this.hotelList.id)
            await this.getHotelById(this.hotelList.id)
            await this.getOrderByHotel(this.hotelList.id)
            await this.getHotelComment(this.hotelList.id)
            this.showOrderList = this.orderList
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
                'set_currentHotelId',
            ]),
            ...mapActions([
                'updateHotelDetail',
                'getBizregions',
                'getHotelById',
                'getHotelByManager',
                'getHotelCoupon',
                'delOrder',
                'delHotel',
                'getUserInfo',
                'getOrderByHotel',
                'getHotelComment',
                'getOrderComment',
                'delRoomById',
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
                this.getOrderComment(record.id)
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
            async delRoom(record) {
                await this.delRoomById(record.id)
                await this.getHotelById(this.hotelList.id)
            },
            //搜索框输入内容
            onSearch() {
                if (this.searchContent === "") {
                    this.showOrderList = this.orderList;
                } else {
                    this.showOrderList = [];
                    this.orderList.forEach(e => {
                        if (String(e.id).includes(this.searchContent)
                            || String(e.hotelName).includes(this.searchContent)
                            || String(e.clientName).includes(this.searchContent)) {
                            this.showOrderList.push(e)
                        }
                    })
                }
            },
            //筛选订单状态
            handleChange(){
                if(this.orderState==="全部"){
                    this.showOrderList = this.orderList;
                }
                else{
                    let tmpArr = [];
                    this.orderList.forEach(e=>{
                    if(e.orderState===this.orderState){
                        tmpArr.push(e)
                        }
                    })
                    this.showOrderList = tmpArr;
                }
            }
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
