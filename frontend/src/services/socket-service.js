import { Stomp } from "@stomp/stompjs";
import SockJS from "sockjs-client";

let stompClient = null;

export function connectWebSocket(userId, callback) {
  const socket = new SockJS(process.env.VITE_PUBLIC_BASE_URL + "/ws");
  stompClient = Stomp.over(socket);

  stompClient.connect({}, () => {
    // Subscribe vào topic để nhận thông báo cho userId cụ thể
    stompClient.subscribe(`/topic/changeUserPackage/${userId}`, (message) => {
      if (message.body) callback(message.body);
    });
  });
}

export function disconnectWebSocket() {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
}
