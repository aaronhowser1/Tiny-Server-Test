package dev.aaronhowser.mods.tinyservertest

import dev.latvian.apps.tinyserver.HTTPServer
import dev.latvian.apps.tinyserver.http.HTTPRequest
import dev.latvian.apps.tinyserver.http.response.HTTPResponse
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.server.ServerStartedEvent
import net.neoforged.neoforge.event.server.ServerStoppedEvent
import java.time.Duration

@EventBusSubscriber(value = [Dist.CLIENT])
object TestEventHandler {

	var server: HTTPServer<HTTPRequest>? = null

	@SubscribeEvent
	fun onServerStart(event: ServerStartedEvent) {
		val s = HTTPServer(::HTTPRequest)

		s.setServerName("TinyServerTest")
		s.setPort(8080)
		s.setDaemon(true)
		s.setKeepAliveTimeout(Duration.ofSeconds(5L))
		s.setMaxKeepAliveConnections(5)

		s.get("/blocks", ::getBlocks)
		s.get("blocks/<id>", ::getBlockInfo)
		s.post("/blocks/{x}/{y}/{z}/<id>", ::setBlock)

		server = s
	}

	@SubscribeEvent
	fun onServerStop(event: ServerStoppedEvent) {
		server?.stop()
		server = null
	}

	fun getBlocks(request: HTTPRequest): HTTPResponse {
		return HTTPResponse.ok().text("Hello, world!")
	}

	fun getBlockInfo(request: HTTPRequest): HTTPResponse {
		val id = request.variable("id").asString()
		return HTTPResponse.ok().text("Block info for id: $id")
	}

	fun setBlock(request: HTTPRequest): HTTPResponse {
		val x = request.variable("x").asInt()
		val y = request.variable("y").asInt()
		val z = request.variable("z").asInt()

		val id = request.variable("id").asString()

		return HTTPResponse.ok().text("Set block at ($x, $y, $z) to id: $id")
	}

}