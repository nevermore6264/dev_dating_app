import { Stomp } from "@stomp/stompjs";
import SockJS from "sockjs-client";

let stompClient = null;

export function connectWebSocket(userId, callback) {
  const socket = new SockJS( + "http://localhost:8088/ws");
  stompClient = Stomp.over(socket);

  stompClient.connect({}, () => {
    console.log("Vao day xxx");

    // Subscribe vào topic để nhận thông báo cho userId cụ thể
    stompClient.subscribe(`/topic/changeUserPackage/${userId}`, (message) => {
      console.log("Vao day");
      if (message.body) callback(message.body);
    });
  });
}

export function disconnectWebSocket() {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
}
