package br.com.rocha.Travel.controller;

import br.com.rocha.Travel.model.Statistic;
import br.com.rocha.Travel.model.Travel;
import br.com.rocha.Travel.service.StatisticService;
import br.com.rocha.Travel.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api-travels/statistics")
public class StatisticController {
	
	private static final Logger logger = Logger.getLogger(StatisticController.class);
	
	@Autowired
	private TravelService travelService;
	
	@Autowired
	private StatisticService statisticsService;
	
	@GetMapping(produces = { "application/json" })
	public ResponseEntity<Statistic> getStatistics() {
		
		List<Travel> travels = travelService.find();
		Statistic statistics = statisticsService.create(travels);
		
		logger.info(statistics);
		
		return ResponseEntity.ok(statistics);
	}
}