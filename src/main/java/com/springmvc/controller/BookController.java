package com.springmvc.controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.springmvc.domain.Book;
import com.springmvc.exception.BookIdException;
import com.springmvc.exception.CategoryException;
import com.springmvc.service.BookService;

@Controller
@RequestMapping("/books")
public class BookController {
	
	@Autowired	//클래스의 프로퍼티에 선언
	private BookService bookService;
	
	@GetMapping
	public String requestBookList(Model model) {
		List<Book> list = bookService.getAllBookList();
		model.addAttribute("bookList", list);
		return "books";
	}
	
	@GetMapping("/all")
	public ModelAndView requestAllBooks() {
		ModelAndView modelAndView = new ModelAndView();
		//ModelAndView 클래스의 modelAndView 인스턴스 생성
		List<Book> list = bookService.getAllBookList();
		modelAndView.addObject("bookList", list);
		modelAndView.setViewName("books");
		return modelAndView;
	}
	
	@GetMapping("/{category}")
	public String requestBooksByCategory(@PathVariable("category") String bookCategory, Model model) {
		List<Book> booksByCategory = bookService.getBookListByCategory(bookCategory);
		
		if(booksByCategory == null || booksByCategory.isEmpty()) {
			throw new CategoryException();
		}
		
		model.addAttribute("bookList", booksByCategory);
		return "books";
	}
	
	@GetMapping("/filter/{bookFilter}")	//매트릭스 변수 사용, 매트릭스 변수 = 값
	public String requestBooksByFilter(@MatrixVariable(pathVar="bookFilter") Map<String, List<String>> bookFilter, Model model) {
		Set<Book> booksByFilter = bookService.getBookListByFilter(bookFilter);
		model.addAttribute("bookList", booksByFilter);
		return "books";
	}
	
	@GetMapping("/book")
	public String requestBookById(@RequestParam("id") String bookId, Model model) {
		//메서드에서 요청 파라미터 id를 bookId로 재정의
		Book bookById = bookService.getBookById(bookId);
		model.addAttribute("book", bookById);
		return "book";
	}
	
	//도서등록
	@GetMapping("/add")
	public String requestAddBookForm(@ModelAttribute("NewBook") Book book) {
		return "addBook";
	}
	
	@PostMapping("/add")
	public String submitAddNewBook(@ModelAttribute("NewBook") Book book) {
		MultipartFile bookImage = book.getBookImage();		//신규 도서 등록 페이지에서 커맨드 객체의 매개변수 중 도서 이미지에 해당하는 매개변수를 MultipartFile 객체의 bookImage 변수로 전달
		
		String saveName = bookImage.getOriginalFilename();	//MultipartFile 타입으로 전송받은 이미지 파일 이름을 얻음.
		File saveFile = new File("C:\\upload", saveName);
		
		if(bookImage != null && !bookImage.isEmpty()) {
			try {
				bookImage.transferTo(saveFile);
			}
			catch (Exception e) {
				throw new RuntimeException("도서 이미지 업로드가 실패하였습니다.", e);
			}
		}
		
		bookService.setNewBook(book);
		//신규 도서 정보를 저장하려고 서비스 객체의 setNewBook() 메서드를 호출
		return"redirect:/books";
		//URL을 강제로 /books로 이동시켜 @RequestMapping("/books")에 매핑
	}
	
	//도서 등록 페이지 제목 출력
	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("addTitle", "신규 도서 등록");
	}
	
	//커스텀 데이터 바인딩
	//<form:input>태그의 file 타입에서 name 속성 이름에 바인딩 되도록
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields("bookId", "name", "unitPrice", "author", "description", "publisher", "category", "unitsInStock", "totalPages", "releaseDate", "condition", "bookImage");
	}
	
	@ExceptionHandler(value= {BookIdException.class})	//예외 클래스 설정
	public ModelAndView handleError(HttpServletRequest req, BookIdException exception) {
		ModelAndView mav = new ModelAndView();	//ModelAndView 클래스의 mav 인스턴스 생성
		mav.addObject("invalidBookId", exception.getBookId());	//모델속성 invalidBookId에서 요청한 도서 아이디를 저장.
		mav.addObject("exception", exception);	//모델속성 exception에서 예외처리 클래스 BookIdException을 저장
		mav.addObject("url", req.getRequestURL()+"?"+req.getQueryString());	//모델 속성 url에서 요청 URL과 요청 쿼리문을 저장
		mav.setViewName("errorBook"); //뷰 이름으로 errorBook을 설정하여 errorBook.jsp 파일을 출력
		return mav;
	}

}
