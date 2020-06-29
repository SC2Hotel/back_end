const getters = {
  //user
  token: state => state.user.token,
  userId: state => state.user.userId,
  userInfo: state => state.user.userInfo,
  userOrderList: state => state.user.userOrderList,
  hotelListLoading: state => state.hotel.hotelListLoading,
  hotelList: state => state.hotel.hotelList,
  bizRegions: state => state.hotel.bizRegions,
  currentHotelInfo: state => state.hotel.currentHotelInfo,
  currentHotelId: state => state.hotel.currentHotelId,
  orderModalVisible: state => state.hotel.orderModalVisible,
  searchModalVisible: state => state.hotel.searchModalVisible,
  orderDetailModalVisible: state => state.user.orderDetailModalVisible,
  orderRateModalVisible: state => state.user.orderRateModalVisible,
  registerVIPModalVisible: state => state.user.registerVIPModalVisible,
  currentOrderRoom: state => state.hotel.currentOrderRoom,
  orderMatchCouponList: state => state.hotel.orderMatchCouponList,
  currentOrder: state => state.user.currentOrder,
  hotelCommentList: state => state.hotel.hotelCommentList,
  hotelDetailModalVisible: state => state.hotel.hotelDetailModalVisible,
  creditLogModalVisible: state => state.user.creditLogModalVisible,
  creditLog: state=> state.user.creditLog,
  //admin
  managerList: state => state.admin.managerList,
  userList: state => state.admin.userList,
  addManagerModalVisible: state => state.admin.addManagerModalVisible,
  addManagerParams: state => state.admin.addManagerParams,
  updataUserInfoModalVisible: state => state.admin.updataUserInfoModalVisible,
  targetUserInfo: state=> state.admin.targetUserInfo,
  //hotelManager
  orderList: state => state.hotelManager.orderList,
  addHotelModalVisible: state => state.hotelManager.addHotelModalVisible,
  addRoomModalVisible: state => state.hotelManager.addRoomModalVisible,
  couponVisible: state => state.hotelManager.couponVisible,
  addCouponVisible: state => state.hotelManager.addCouponVisible,
  activeHotelId: state => state.hotelManager.activeHotelId,
  couponList: state => state.hotelManager.couponList,
  commentItem: state => state.hotelManager.commentItem
  }
  
  export default getters
