/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.fyp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Controller
@EnableCaching
@EnableScheduling
@SpringBootApplication
//@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.DELETE})
public class SpringExampleApp {

	public static final String QRCODE_ENDPOINT = "/qrcode";
	public static final long THIRTY_MINUTES = 30000;
	@Autowired
	ImageService imageService;

	public static void main(String[] args) {
		SpringApplication.run(SpringExampleApp.class, args);
	}


	public Mono<ResponseEntity<byte[]>> getQRCode(String text) {
		return imageService.generateQRCode(text, 256, 256).map(imageBuff ->  
			ResponseEntity.ok().cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES)).body(imageBuff)
		);
	}

//	@Scheduled(fixedRate = THIRTY_MINUTES)
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	@DeleteMapping(value = QRCODE_ENDPOINT)
//	public void deleteAllCachedImages() {
//		imageService.purgeCache();
//	}
//
//	// Workaround, see: https://github.com/spring-projects/spring-boot/issues/9785
//	@Bean
//	public RouterFunction<ServerResponse> indexRouter(@Value("classpath:/static/index.html") final Resource indexHtml) {
//		return route(GET("/"), request -> ok().contentType(MediaType.TEXT_HTML).bodyValue(indexHtml));
//	}
//
//	@ExceptionHandler(RuntimeException.class)
//	public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
//		return ResponseEntity.status(500).contentType(MediaType.TEXT_PLAIN).body(ex.getMessage());
//	}
}