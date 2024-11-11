package RecuperatorioWS.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Service
public class ChatHandler extends TextWebSocketHandler{
	
	private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<WebSocketSession>();
	private static final List<TextMessage> messages = new ArrayList<>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception{
		sessions.add(session);
		for(TextMessage m : messages) {
			session.sendMessage(m);
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
		sessions.add(session);
	}
	
	@Override
	public void handleTextMessage(WebSocketSession session,TextMessage message) throws Exception {	
		
		String payload = message.toString();
		messages.add(message);
		
		if(payload.contains("123ABC")) {
			String mensaje1 = "se conecto CORRECTAMENTE";
			session.sendMessage(new TextMessage(mensaje1));
		}
		else {
			String mensaje2 = "se conecto INCORRECTAMENTE";
			session.sendMessage(new TextMessage(mensaje2));
		}
		for(WebSocketSession s : sessions) {
			s.sendMessage(message);
		}
	}
	
}
