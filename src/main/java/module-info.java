module UniLab.server {
	requires java.logging;
	requires org.apache.logging.log4j;
	requires org.apache.logging.log4j.core;
	requires io.github.cdimascio.dotenv.java;
	requires java.rmi;
	requires java.sql;
	requires static lombok;
	requires static java.compiler;

	exports org.a3b.commons;
	exports org.a3b.commons.magazzeno;
	exports org.a3b.commons.result;
	exports org.a3b.commons.utils;

	opens org.a3b.commons;
	opens org.a3b.commons.magazzeno;
	opens org.a3b.commons.result;
	opens org.a3b.commons.utils;
}