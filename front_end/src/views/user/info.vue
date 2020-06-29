<template>
    <div class="info-wrapper">
        <a-tabs>
            <a-tab-pane tab="我的信息" key="1">
                <a-form :form="form" style="margin-top: 30px">

                    <a-form-item label="用户名" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <a-input
                                placeholder="请填写用户名"
                                v-decorator="['userName', { rules: [{ required: true, message: '请输入用户名' }] }]"
                                v-if="modify"
                        />
                        <span v-else>{{ userInfo.userName }}</span>
                    </a-form-item>
                    <a-form-item label="账号类型" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>
                            {{userInfo.userType}}
                        </span>
                        <a-button v-if="userInfo.userType=='Client'" style="margin-left: 40px" @click="showVIPModal">
                            注册会员
                        </a-button>
                        <register-v-i-p-modal></register-v-i-p-modal>
                    </a-form-item>
                    <a-form-item label="邮箱" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ userInfo.email }}</span>
                    </a-form-item>

                    <a-form-item label="手机号" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <a-input
                                placeholder="请填写手机号"
                                v-decorator="['phoneNumber', { rules: [{ required: true, message: '请输入手机号' }] }]"
                                v-if="modify"
                        />
                        <span v-else>{{ userInfo.phoneNumber}}</span>
                    </a-form-item>
                    <a-form-item label="信用值" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ userInfo.credit }}</span>
                        <span style="margin-left: 30px;color: dodgerblue" @click="showCreditlog">查看信用记录</span>
                    </a-form-item>
                    <a-form-item label="密码" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }"
                                 v-if="modify">
                        <a-input
                                placeholder="请输入新密码"
                                v-decorator="['password', { rules: [{ required: false, message: '请输入密码(为空为不修改)' }] }]"
                        />
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify">
                        <a-button type="primary" @click="saveModify">
                            保存
                        </a-button>
                        <a-button type="default" style="margin-left: 30px" @click="cancelModify">
                            取消
                        </a-button>
                    </a-form-item>
                    <a-form-item style="margin-left: 100px" v-else>
                        <a-button type="primary" @click="modifyInfo">
                            修改信息
                        </a-button>
                    </a-form-item>
                </a-form>
            </a-tab-pane>
            <a-tab-pane tab="我的订单" key="2">
                <a-input-search placeholder="请输入id或酒店名" enter-button @search="onSearch" style="width: 30%" v-model="searchContent"/>
                <a-table
                        style="margin-top: 20px"
                        :columns="columns"
                        :dataSource="showOrderList"
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
                    <a-tag slot="orderState" :color=" text === '已预订' ? 'blue': text === '异常' ? 'red' : text === '已执行' ? 'green' : text === '已撤销' ? 'orange' : text==='超过最迟延时入住期限' ? 'grey':'' " slot-scope="text">
                        {{ text }}
                    </a-tag>
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="showOrderDetailModal(record)">查看</a-button>
                        <a-divider type="vertical" v-if="record.orderState == '已预订'"></a-divider>

                        <a-popconfirm
                                title="你确定撤销该笔订单吗？"
                                @confirm="confirmCancelOrder(record.id)"
                                @cancel="cancelCancelOrder"
                                okText="确定"
                                cancelText="取消"
                                v-if="record.orderState == '已预订'"
                        >
                            <a-button type="danger" size="small">撤销</a-button>
                        </a-popconfirm>
                        <a-divider type="vertical" v-if="record.orderState == '已执行'"></a-divider>
                        <a-button size="small" @click="showOrderRateModal(record)" v-if="record.orderState == '已执行'">评价</a-button>
                    </span>
                </a-table>
            </a-tab-pane>
        </a-tabs>
        <credit-log-modal></credit-log-modal>
        <order-rate-modal></order-rate-modal>
        <OrderDetailModal></OrderDetailModal>
    </div>
</template>
<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'

    import OrderDetailModal from "./components/orderDetailModal";
    import OrderRateModal from "./components/orderRateModal";
    import RegisterVIPModal from "./components/registerVIPModal";
    import CreditLogModal from "./components/creditLogModal";

    const columns = [
        {
            title: '订单号',
            dataIndex: 'id',
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

    ];
    export default {
        name: 'info',
        data() {
            return {
                modify: false,
                formLayout: 'horizontal',
                pagination: {},
                columns,
                data: [],
                form: this.$form.createForm(this, {name: 'coordinated'}),
                showOrderList:[],//用于展示的数组
                searchContent:'',//搜索的文本
            }
        },
        components: {CreditLogModal, RegisterVIPModal, OrderRateModal, OrderDetailModal},
        computed: {
            ...mapGetters([
                'userId',
                'userInfo',
                'userOrderList',
                'orderDetailModalVisible'
            ])
        },
        async mounted() {
            await this.getUserInfo()
            await this.getUserOrders()
            this.showOrderList = this.userOrderList;
        },
        methods: {
            ...mapActions([
                'getUserInfo',
                'getUserOrders',
                'updateUserInfo',
                'cancelOrder',
                'getCreditLog'
            ]),
            ...mapMutations([
                'set_orderDetailModalVisible',
                'set_orderRateModalVisible',
                'set_currentOrder',
                'set_registerVIPModalVisible',
                'set_creditLogModalVisible'
            ]),
            saveModify() {
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            userName: this.form.getFieldValue('userName'),
                            phoneNumber: this.form.getFieldValue('phoneNumber'),
                            password: this.form.getFieldValue('password')?this.form.getFieldValue('password'):''
                        }
                        this.updateUserInfo(data).then(() => {
                            this.modify = false
                        })
                    }
                });
            },
            modifyInfo() {
                setTimeout(() => {
                    this.form.setFieldsValue({
                        'userName': this.userInfo.userName,
                        'phoneNumber': this.userInfo.phoneNumber,
                    })
                }, 0)
                this.modify = true
            },
            cancelModify() {
                this.modify = false
            },
            confirmCancelOrder(orderId) {
                this.cancelOrder(orderId)
            },
            cancelCancelOrder() {
            },
            showOrderDetailModal(record){
                this.set_currentOrder(record)
                this.set_orderDetailModalVisible(true)
            },
            showOrderRateModal(record){
                this.set_currentOrder(record)
                this.set_orderRateModalVisible(true)
            },
            showVIPModal(){
                this.set_registerVIPModalVisible(true)
            },
            //搜索框输入内容
            onSearch(){
                if(this.searchContent===""){
                    this.showOrderList = this.userOrderList;
                }else{
                    this.showOrderList = [];
                    this.userOrderList.forEach(e=>{
                        if(String(e.id).includes(this.searchContent)
                        ||String(e.hotelName).includes(this.searchContent)){
                            this.showOrderList.push(e)
                        }
                    })
                }
            },
            showCreditlog(){
                this.getCreditLog(this.userInfo.id)
                console.log(this.userInfo.id)
                this.set_creditLogModalVisible(true)
            }

        }
    }
</script>
<style scoped lang="less">
    .info-wrapper {
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
    .info-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">

</style>
