<template>
    <div class="subscription-plans">
      <h1>Các Gói Thành Viên</h1>
      <div class="plans-container">
        <!-- Free Plan -->
        <div class="plan free">
          <h2>Gói Free</h2>
          <p>Truy cập cơ bản với các tính năng miễn phí.</p>
          <ul>
            <li>Gửi 5 tin nhắn mỗi ngày</li>
            <li>Xem thông tin cơ bản của người dùng</li>
            <li>Truy cập miễn phí các địa điểm nổi bật</li>
          </ul>
          <button @click="selectPlan('free')">Chọn Gói Free</button>
        </div>
  
        <!-- Trial Plan -->
        <div class="plan trial">
          <h2>Gói Trial</h2>
          <p>Thử nghiệm các tính năng cao cấp trong 7 ngày.</p>
          <ul>
            <li>Gửi tin nhắn không giới hạn</li>
            <li>Không có quảng cáo</li>
            <li>Xem ai đã xem và thích hồ sơ của bạn</li>
            <li>Truy cập toàn bộ các tính năng cao cấp</li>
          </ul>
          <button @click="selectPlan('trial')">Dùng Thử 7 Ngày Miễn Phí</button>
        </div>
  
        <!-- Premium Plan -->
        <div class="plan premium">
          <h2>Gói Premium</h2>
          <p>Truy cập đầy đủ các tính năng cao cấp.</p>
          <ul>
            <li>Gửi tin nhắn không giới hạn</li>
            <li>Truy cập các vị trí hẹn hò độc quyền</li>
            <li>Ưu tiên hiển thị hồ sơ</li>
            <li>Xem ai đã thích và nhắn tin với bạn trước</li>
            <li>Tắt quảng cáo hoàn toàn</li>
          </ul>
          <button @click="selectPlan('premium')">Nâng Cấp Gói Premium</button>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  
  const selectedPlan = ref('');
  
  const selectPlan = (plan) => {
    selectedPlan.value = plan;
    alert(`Bạn đã chọn gói: ${plan.toUpperCase()}`);
    // Thực hiện logic khác như chuyển trang hoặc gọi API để xử lý nâng cấp gói
  };
  </script>
  
  <style scoped>
  .subscription-plans {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
    text-align: center;
  }
  
  .plans-container {
    display: flex;
    justify-content: space-between;
    gap: 20px;
    flex-wrap: wrap;
  }
  
  .plan {
    flex: 1;
    min-width: 300px;
    background-color: #f9f9f9;
    border: 1px solid #ccc;
    border-radius: 8px;
    padding: 20px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }
  
  .plan h2 {
    margin-bottom: 10px;
  }
  
  .plan p {
    font-size: 14px;
    color: #555;
    margin-bottom: 15px;
  }
  
  .plan ul {
    list-style-type: none;
    padding: 0;
    margin-bottom: 15px;
    flex-grow: 1; /* Đẩy phần tử này chiếm toàn bộ khoảng trống */
  }
  
  .plan li {
    margin-bottom: 8px;
    font-size: 14px;
  }
  
  button {
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 5px;
    padding: 10px;
    cursor: pointer;
    transition: background-color 0.2s;
    width: 100%; /* Đảm bảo nút chiếm toàn bộ chiều rộng của plan */
    margin-top: auto; /* Đẩy nút xuống dưới cùng */
  }
  
  button:hover {
    background-color: #0056b3;
  }
  
  .free {
    border-color: #28a745;
  }
  
  .trial {
    border-color: #ffc107;
  }
  
  .premium {
    border-color: #007bff;
  }
  </style>
  