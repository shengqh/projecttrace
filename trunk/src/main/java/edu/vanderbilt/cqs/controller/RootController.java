package edu.vanderbilt.cqs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({ "currentuser" })
public class RootController {

}
