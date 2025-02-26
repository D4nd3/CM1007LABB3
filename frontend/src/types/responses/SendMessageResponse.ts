export interface SendMessageResponse {
  id: number;
  text: string;
  senderId: string;
  senderName: String;
  receiverId: string;
  receiverName: String;
  timestamp: bigint;
  isRead: boolean;
}