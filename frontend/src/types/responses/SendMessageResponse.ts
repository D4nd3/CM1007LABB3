export interface SendMessageResponse {
  id: number;
  text: string;
  senderId: number;
  senderName: String;
  receiverId: number;
  receiverName: String;
  timestamp: bigint;
  isRead: boolean;
}