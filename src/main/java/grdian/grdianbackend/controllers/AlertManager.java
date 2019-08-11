package grdian.grdianbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import grdian.grdianbackend.repos.AlertRepo;
import grdian.grdianbackend.repos.GrdianRepo;

@Service
public class AlertManager {

	@Autowired
	private GrdianRepo grdianRepo;

	@Autowired
	private AlertRepo alertRepo;
	
	

}
