import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";

let stompClient = null;

export function connectWebSocket(userId, callback) {
  const socket = new SockJS("http://localhost:8088/ws"); // This should match the backend's endpoint
  stompClient = Stomp.over(socket);

  stompClient.connect({}, (frame) => {
    console.log('Connected: ' + frame);

    // Subscribe to the topic for notifications
    stompClient.subscribe(`/topic/changeUserPackage/${userId}`, (message) => {
      console.log("Message received:", message);
      if (message.body) callback(message.body);
    });
  });
}

export function disconnectWebSocket() {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
}
