import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint("/chat")
public class ChatServer{
    public Logger logger;
    private static List<Session> ServerSessions;

    public static void setServerSessions(List<Session> serverSessions) {
        ServerSessions = serverSessions;
    }
    public static List<Session> getServerSessions()
    {
        return ServerSessions;
    }

    @OnOpen
    public void peerConnected(Session session){
        //armazena a sessão
        try {
            List<Session> serverSessions = getServerSessions();
            serverSessions.add(session);
            setServerSessions(serverSessions);
            logger.log(Level.INFO, "Sessão de ID " + session.getId() + " estabelecida.");
        }
        catch(Exception exception){
            logger.log(Level.SEVERE, "Erro ao estabelecer sessão. Exception:"+exception);
        }
    }
    @OnClose
    public void peerDisconnected(Session session){
        //remove a sessão
        try {
            List<Session> serverSessions = getServerSessions();
            serverSessions.remove(session);
            setServerSessions(serverSessions);
            logger.log(Level.INFO, "Sessão de ID " + session.getId() + " removida.");
        }
        catch(Exception exception){
            logger.log(Level.SEVERE, "Erro ao desconectar sessão. Exception:"+exception);
        }
    }

    @OnMessage
    public void messagePeers(String message){
        List<Session> serverSessions = getServerSessions();
        try{
            for(Session s : serverSessions){
                s.getBasicRemote().sendText(message);
                logger.log(Level.INFO, "Sessão de ID " + s.getId() + " notificada.");
            }
        }
        catch(Exception exception){
            logger.log(Level.SEVERE, "Erro ao enviar mensagens para peers. Exception:"+exception);
        }
    }
}

