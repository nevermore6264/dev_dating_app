<template>
  <div>
  </div>
</template>

<script>
import {onMounted} from 'vue';
import {connectSSE} from '@/services/sse-client';
import {ElNotification} from 'element-plus';

export default {
  name: 'UserNotification',
  setup() {
    onMounted(() => {
      console.log("UserNotification component mounted");
      let lastMessage = null;

      connectSSE((message) => {
        console.log("Received message:", message);
        localStorage.setItem("plan", message);

        // Kiểm tra nếu role không phải là "admin" thì hiển thị thông báo
        if (message !== lastMessage) {  // Chỉ hiển thị khi tin nhắn khác với tin nhắn trước
          lastMessage = message;        // Cập nhật tin nhắn cuối cùng
          const role = localStorage.getItem("role");

          if (role !== "admin") {
            ElNotification({
              title: 'Notification',
              message: "Your package has been updated to: " + message,
              type: 'success',
              duration: 5000
            });
          }
        }
      });
    });
  }
};
</script>
