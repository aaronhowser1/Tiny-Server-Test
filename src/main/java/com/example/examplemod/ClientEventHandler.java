package com.example.examplemod;

import dev.latvian.apps.tinyserver.HTTPServer;
import dev.latvian.apps.tinyserver.http.HTTPRequest;
import dev.latvian.apps.tinyserver.http.response.HTTPResponse;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

import javax.annotation.Nullable;
import java.time.Duration;

@EventBusSubscriber(value = Dist.CLIENT, modid = ExampleMod.MODID)
public class ClientEventHandler {

	@Nullable
	private static HTTPServer<HTTPRequest> server = null;

	@SubscribeEvent
	public static void onServerStart(ServerStartedEvent event) {
		var s = new HTTPServer<>(HTTPRequest::new);

		s.setServerName("Aaron Test");
		s.setPort(8080);
		s.setDaemon(true);
		s.setKeepAliveTimeout(Duration.ofSeconds(5L));
		s.setMaxKeepAliveConnections(5);
		s.get("/blocks", ClientEventHandler::getBlocks);
		s.get("/blocks/{namespace}/{id}", ClientEventHandler::getBlockInfo);
		s.post("/blocks/{x}/{y}/{z}/{namespace}/{id}", ClientEventHandler::postBlock);

		System.out.println("Started server at http://localhost:" + s.start());

		server = s;
	}

	@SubscribeEvent
	public static void serverStopped(ServerStoppingEvent event) {
		if (server != null) {
			server.stop();
			server = null;
		}
	}

	public static HTTPResponse getBlocks(HTTPRequest req) {
		return HTTPResponse.ok().text("Hello, World!");
	}

	public static HTTPResponse getBlockInfo(HTTPRequest req) {
		String namespace = req.variable("namespace").asString();
		String id = req.variable("id").asString();
		return HTTPResponse.ok().text(String.format("Block: %s:%s", namespace, id));
	}

	public static HTTPResponse postBlock(HTTPRequest req) {
		int x = req.variable("x").asInt();
		int y = req.variable("y").asInt();
		int z = req.variable("z").asInt();
		String namespace = req.variable("namespace").asString();
		String id = req.variable("id").asString();
		return HTTPResponse.ok().text(String.format("Set block at (%d, %d, %d) to %s:%s", x, y, z, namespace, id));
	}

}
