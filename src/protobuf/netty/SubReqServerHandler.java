package protobuf.netty;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import protobuf.SubscribeReqProto;
import protobuf.SubscribeRespProto;
import protobuf.SubscribeRespProto.SubscribeResp;
import protobuf.SubscribeRespProto.SubscribeResp.Builder;

public class SubReqServerHandler extends ChannelHandlerAdapter  {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq)msg;
		if ("Lilinfeng".equalsIgnoreCase(req.getUserName())) {
			System.out.println("Server accept client subscribe req :["+ req.toString()+"]");
			ctx.write(resp(req.getSubReqID()));
		}
		ctx.flush();
	}
	
	
	private SubscribeRespProto.SubscribeResp resp(int subReqID){
		SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
		builder.setSubReqID(subReqID);
		builder.setRespCode(0);
		builder.setDesc("Netty book order succeed, 3 days later, sent to the designated address");
		return builder.build();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();//发生异常，链路关闭
	}
}
