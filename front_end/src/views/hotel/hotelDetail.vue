<template>
    <a-layout>
        <a-layout-content>
            <div class="hotelDetailCard">
                <h1>
                    {{ currentHotelInfo.title }}
                </h1>
                <div class="hotel-info">
                    <a-card style="width: 240px">
                        <img
                                alt="example"
                                src="@/assets/cover.jpeg"
                                slot="cover"
                                referrerPolicy="no-referrer"
                        />
                    </a-card>
                    <div class="info">
                        <div class="items" v-if="currentHotelInfo.name">
                            <span class="label">酒店名称：</span>
                            <span class="value">{{ currentHotelInfo.name }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.address">
                            <span class="label">地址</span>
                            <span class="value">{{ currentHotelInfo.address }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.rate">
                            <span class="label">评分:</span>
                            <span class="value">{{ currentHotelInfo.rate }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.hotelStar">
                            <span class="label">星级:</span>
                            <a-rate style="font-size: 15px" :value="currentHotelInfo.rate" disabled allowHalf/>
                        </div>
                        <div class="items" v-if="currentHotelInfo.description">
                            <span class="label">酒店简介:</span>
                            <span class="value">{{ currentHotelInfo.description }}</span>
                        </div>
                    </div>
                </div>
                <a-divider></a-divider>
                <a-tabs>
                    <a-tab-pane tab="房间信息" key="1">
                        <RoomList :rooms="currentHotelInfo.rooms"></RoomList>
                    </a-tab-pane>
                    <a-tab-pane tab="酒店详情" key="2">
                        <HotelDetail></HotelDetail>
                    </a-tab-pane>
                    <a-tab-pane tab="酒店评价" key="3">
                        <a-comment v-for="item in hotelCommentList" :key="item.id">
                            <a slot="author">{{item.score}}</a>
                            <p slot="content">
                                {{item.content}}
                            </p>
                            <a-tooltip slot="datetime" :title="moment().format('YYYY-MM-DD HH:mm:ss')">
                                <span>{{ moment().fromNow() }}</span>
                            </a-tooltip>
                        </a-comment>
                    </a-tab-pane>
                    <a-tab-pane tab="我的订单" key="4">
                        <a-table
                                :columns="columns1" :data-source="userOrderList" bordered>
                            <a-tag slot="orderState" :color=" text === '已预订' ? 'blue': text === '异常' ? 'red' : text === '已执行' ? 'green' : text === '已撤销' ? 'orange' : text==='超过最迟延时入住期限' ? 'grey':'' " slot-scope="text">
                                {{ text }}
                            </a-tag>
                        </a-table>
                    </a-tab-pane>
                </a-tabs>
            </div>
        </a-layout-content>
    </a-layout>
</template>
<script>
    import {mapGetters, mapActions, mapMutations} from 'vuex'
    import RoomList from './components/roomList'
    import HotelDetail from './components/hotelDetail'
    import moment from 'moment';

    const columns1= [
        {
            title: "订单号",
            dataIndex: "id",
            key:"id"
        },
        {
            title:"入住时间",
            dataIndex:"checkInDate"
        },
        {
            title:"离店时间",
            dataIndex:"checkOutDate"
        },
        {
            title: '订单状态',
            filters: [{text: '已预订', value: '已预订'}, {text: '已撤销', value: '已撤销'}, {text: '已执行', value: '已执行'},{text:'异常',value:'异常'},{text:'超过最迟延时入住期限',value:'超过最迟延时入住期限'}],
            onFilter: (value, record) => record.orderState.includes(value),
            dataIndex: 'orderState',
            scopedSlots: {customRender: 'orderState'}
        },
        {
            title:"预定房间数",
            dataIndex:"roomNum"
        },
        {
            title:"房间类型",
            dataIndex:"roomType"
        }
    ]
    export default {
        name: 'hotelDetail',
        components: {
            RoomList,
            HotelDetail,
        },
        data() {
            return {
                moment,
                myOrders: [],
                columns1
            }
        },
        computed: {
            ...mapGetters([
                'currentHotelInfo',
                'hotelCommentList',
                'userOrderList',
                'userInfo'
            ])
        },
        mounted() {
            this.set_currentHotelId(Number(this.$route.params.hotelId))
            this.getHotelComment(Number(this.$route.params.hotelId))
            this.getHotelById()
            this.getUserHotelOrders({hotelId:Number(this.$route.params.hotelId),userId:this.userInfo.id})
        },
        beforeRouteUpdate(to, from, next) {
            this.set_currentHotelId(Number(to.params.hotelId))
            this.getHotelById()
            next()
        },
        methods: {
            ...mapMutations([
                'set_currentHotelId',
            ]),
            ...mapActions([
                'getHotelById',
                'getHotelComment',
                'getUserHotelOrders',
            ])
        }
    }
</script>
<style scoped lang="less">
    .hotelDetailCard {
        padding: 50px 50px;
    }

    .hotel-info {
        display: flex;
        align-items: stretch;
        justify-content: flex-start;

        .info {
            padding: 10px 0;
            display: flex;
            flex-direction: column;
            margin-left: 20px;

            .items {
                display: flex;
                align-items: center;
                margin-bottom: 10px;

                .label {
                    margin-right: 10px;
                    font-size: 18px;
                }

                .value {
                    margin-right: 15px
                }
            }
        }
    }
</style>
