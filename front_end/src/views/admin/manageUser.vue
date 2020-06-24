<template>
    <div class="manageUser-wrapper">
        <a-tabs>
            <a-tab-pane tab="管理员管理" key="1">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addManager"><a-icon type="plus" />添加酒店管理员</a-button>
                </div>
                <a-table
                    :columns="columns"
                    :dataSource="managerList"
                    bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="action" slot-scope="text, record">
                        <a-button type="danger" @click="delManager(record)">删除用户</a-button>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="酒店详情" key="2">
                <a-table
                        :columns="columns2"
                        :dataSource="hotelList"
                        bordered
                >
                </a-table>
            </a-tab-pane>
        </a-tabs>
        <AddManagerModal></AddManagerModal>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddManagerModal from './components/addManagerModal'
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
        }
    },
    components: {
        AddManagerModal
    },
    computed: {
        ...mapGetters([
            'addManagerModalVisible',
            'managerList',
            'hotelList'
        ])
    },
    mounted() {
      this.getManagerList();
        this.getHotelList();
    },
    methods: {
        ...mapActions([
            'getManagerList',
            'getHotelList',
            'delHotelManager'
        ]),
        ...mapMutations([
            'set_addManagerModalVisible',
        ]),
        addManager(){
            this.set_addManagerModalVisible(true);
        },
        async delManager(record){
            await this.delHotelManager(record.id);
            this.getManagerList();
            this.getHotelList();
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
