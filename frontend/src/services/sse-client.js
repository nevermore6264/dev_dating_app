// sse-client.js
export function connectSSE(callback) {
  const eventSource = new EventSource("http://localhost:8088/api/subscribe");

  eventSource.onmessage = function(event) {
    console.log("Notification:", event.data);
    callback(event.data); // Gọi hàm callback với dữ liệu sự kiện
  };

  eventSource.onerror = function(error) {
    console.error("SSE connection error:", error);
  };
}
