<template>
  <div>
    <!-- Bạn có thể thêm nội dung ở đây nếu cần -->
  </div>
</template>

<script>
import { ref, onMounted, onUnmounted, watch } from 'vue';
import { ElNotification } from 'element-plus';
import 'element-plus/es/components/notification/style/css'; // Import style
import { connectWebSocket, disconnectWebSocket } from '@/services/socket-service';

export default {
  name: 'UserNotification',
  setup() {
    const notification = ref('');

    // Hàm kết nối WebSocket
    onMounted(() => {
      console.log("xxxxxxxxxxxxxxxx")
      const userId = localStorage.getItem('userId');
      console.log('Connecting to WebSocket for user ID:', userId);
      connectWebSocket(userId, (message) => {
        console.log('Received message:', message);
        notification.value = message; // Cập nhật notification khi nhận tin nhắn
      });
    });

    // Ngắt kết nối WebSocket khi component bị hủy
    onUnmounted(() => {
      disconnectWebSocket();
    });

    // Theo dõi sự thay đổi của notification và hiển thị thông báo
    watch(notification, (newMessage) => {
      if (newMessage) {
        ElNotification({
          title: 'Notification',
          message: newMessage,
          type: 'success',
          duration: 5000, // Hiển thị thông báo trong 5 giây
          position: 'top-right',
        });
      }
    });

    return {notification};
  }
};
</script>

<style scoped>
/* Thêm style nếu cần */
</style>
