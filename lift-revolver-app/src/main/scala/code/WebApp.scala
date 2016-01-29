package code

import java.net.InetSocketAddress

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext

import net.liftweb.common.Loggable
import net.liftweb.util.{ LoggingAutoConfigurer, Props }

object WebApp extends App with Loggable {
  LoggingAutoConfigurer().apply()

  logger.info("run.mode: " + Props.mode.toString)
  logger.trace("system environment: " + sys.env)
  logger.trace("system props: " + sys.props)
  logger.trace("liftweb props: " + Props.props)

  val port = Props.get("jetty.port", "8081").toInt
  val context = Props.get("jetty.context", "/")

  // webappContext
  val webappPath = getClass.getClassLoader.getResource("webapp").toExternalForm
  val webappContext = new WebAppContext(webappPath, context)

  // don't allow directory browsing
  webappContext.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false")

  // start the server
  val address = new InetSocketAddress(port)
  val server = new Server(address)
  server.setHandler(webappContext)
  server.start()
  logger.info(s"Lift server started on port $port")
  server.join()
}
