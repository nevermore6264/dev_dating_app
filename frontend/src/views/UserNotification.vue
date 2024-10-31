<template>
  <div>
    <p> xxxxxxxxxxxx {{ notification }}</p>
  </div>
</template>

<script>
import { ref, onMounted, onUnmounted } from 'vue';
import { connectWebSocket, disconnectWebSocket } from '@/services/socket-service';

export default {
  name: 'UserNotification',
  setup() {
    const notification = ref('');

    onMounted(() => {
      const userId = '7'; // ID của người dùng hiện tại
      connectWebSocket(userId, (message) => {
        console.log("Received message:", message); // Kiểm tra tin nhắn
        notification.value = message;
      });
    });

    onUnmounted(() => {
      disconnectWebSocket();
    });

    return {notification};
  }
};
</script>

<style scoped>
p {
  color: green;
  font-size: 1.2em;
  font-weight: bold;
}
</style>
