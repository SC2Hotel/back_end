<template>
    <div class="manageUser-wrapper">
        <a-tabs>
            <a-tab-pane tab="用户管理" key="1">
                <div style="width: 100%; display: flex;flex-direction: row;">
                    <a-input-search placeholder="请输入id、用户邮箱或用户名" enter-button @search="onSearch" style="width: 30%;margin-right: auto" v-model="searchContent"/>
                    <a-select style="width: 20%;margin-right: auto" @change="selectUserState" placeholder="账号类型" v-model="userState">
                        <a-select-option value="全部">
                            全部
                        </a-select-option>
                        <a-select-option value="Client">
                            Client
                        </a-select-option>
                        <a-select-option value="HotelManager">
                            HotelManager
                        </a-select-option>
                        <a-select-option value="Admin">
                            Admin
                        </a-select-option>
                    </a-select>
                    <a-button type="primary" @click="addManager"><a-icon type="plus" />添加酒店管理员</a-button>
                </div>

                <a-table
                        style="margin-top: 10px"
                    :columns="columns"
                    :dataSource="showUserList"
                        rowKey="id"
                    bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <a-tag slot="userType" :color=" text === 'Client' ? 'blue': text === 'HotelManager' ? 'green': 'commonSeniorClient' === text ? 'gold':'red'  " slot-scope="text">
                        {{ text }}
                    </a-tag>
                    <span slot="action" slot-scope="record">
                        <a-button type="danger" @click="delManager(record)">删除用户</a-button>
                        <a-button type="default" @click="reset(record)" style="margin-left: 10px">重置密码</a-button>
                        <a-button type="primary" @click="updateUserInfo(record)" style="margin-left: 10px">更新信息</a-button>
                        <a-button type="primary" @click="toUpdateUserCredit(record)" style="margin-left: 10px">增加信用值</a-button>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="酒店详情" key="2">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addHotel"><a-icon type="plus" />添加酒店</a-button>
                </div>
                <add-hotel-modal></add-hotel-modal>
                <a-table
                        :columns="columns2"
                        :dataSource="hotelList"
                        bordered
                >
                </a-table>
            </a-tab-pane>
        </a-tabs>
        <AddManagerModal></AddManagerModal>
        <update-user-infomodal></update-user-infomodal>
<!--        修改信用model-->
        <a-modal v-model="creditModalVisible" title="增加信用" @ok="submitUpdateCredit">
            <a-input-number id="inputNumber" v-model="updateCreditNum" :min="1" />
        </a-modal>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddManagerModal from './components/addManagerModal'
import AddHotelModal from "./components/addHotelModal";
import UpdateUserInfomodal from "./components/updateUserInfomodal";
const columns = [
    {
        title: '用户id',
        dataIndex: 'id',
    },
    {
        title: '用户邮箱',
        dataIndex: 'email',
    },
    {
        title: '用户名',
        dataIndex: 'userName',
    },
    {
        title: '用户密码',
        dataIndex: 'password',
    },
    {
        title: '用户手机号',
        dataIndex: 'phoneNumber',
    },
    {
        title: '信用值',
        dataIndex: 'credit',
    },
    {
        title: '账号类型',
        dataIndex: 'userType',
        scopedSlots: {customRender: 'userType'}
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
  ];
const columns2 = [
    {
        title: '酒店id',
        dataIndex: 'id',
    },
    {
        title: '酒店名称',
        dataIndex: 'name',
    },
    {
        title: '管理员id',
        dataIndex: 'managerId',
    },
    // {
    //     title: '操作',
    //     key: 'action',
    //     scopedSlots: { customRender: 'action' },
    // },
];

export default {
    name: 'manageHotel',
    data(){
        return {
            formLayout: 'horizontal',
            pagination: {},
            columns,
            columns2,
            data: [],
            form: this.$form.createForm(this, { name: 'manageUser' }),
            searchContent: "",//搜索的内容
            showUserList:[],//展示的列表
            userState:"账号类型",//用户账号类型
            creditModalVisible:false,
            updateCreditNum:1,
            userId:0,
        }
    },
    components: {
        UpdateUserInfomodal,
        AddHotelModal,
        AddManagerModal
    },
    computed: {
        ...mapGetters([
            'addHotelModalVisible',
            'addManagerModalVisible',
            'userList',
            'hotelList',
            'targetUserInfo'
        ])
    },
    async mounted() {
      await this.getAllUsersList();
      this.showUserList = this.userList.slice();
    },
    methods: {
        ...mapActions([
            'getUsersList',
            'getAllUsersList',
            'delHotelManager',
            'resetPassword',
            'getTargetUserInfo',
            'updateUserCredit'
        ]),
        ...mapMutations([
            'set_addManagerModalVisible',
            'set_addHotelModalVisible',
            'set_updataUserInfoModalVisible',
            'set_targetUserInfo',
        ]),
        addManager(){
            this.set_addManagerModalVisible(true);
        },
        addHotel(){
            this.set_addHotelModalVisible(true);
        },
        async delManager(record){
            await this.delHotelManager(record.id);
            this.getAllUsersList();
        },
        reset(record){
            // console.log(record)
            this.resetPassword(record.id)
        },
        updateUserInfo(record){
            this.set_targetUserInfo(record.id)
            this.set_updataUserInfoModalVisible(true)
        },
        //修改用户信用值
        toUpdateUserCredit(record){
            this.userId = record.id
            this.creditModalVisible=true
        },
        //提交修改信用值申请
        async submitUpdateCredit(){
            await this.updateUserCredit({id:this.userId,creditNum:this.updateCreditNum})
            await this.getAllUsersList();
            this.showUserList = this.userList.slice();
            this.creditModalVisible=false
        },
        onSearch(){
            if (this.searchContent === "") {
                this.showUserList = this.userList;
                return;
            }
            this.showUserList = [];
            this.userList.forEach(e => {
                if (String(e.id).includes(this.searchContent)
                    || String(e.email).includes(this.searchContent)
                    || String(e.userName).includes(this.searchContent)) {
                    this.showUserList.push(e)
                }
            })
        },
        //筛选用户类型
        selectUserState(){
            if(this.userState==="全部"){
                this.showUserList = this.userList;
                return;
            }
            let tmpArr = [];
            this.userList.forEach(e=>{
                if(e.userType===this.userState){
                    tmpArr.push(e)
                }
            })
            this.showUserList = tmpArr;
        }
    }
}
</script>
<style scoped lang="less">
    .manageUser-wrapper {
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
    .manageUser-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">

</style>
