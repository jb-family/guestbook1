package ino.web.freeBoard.controller;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.javassist.expr.Instanceof;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ino.web.freeBoard.dto.FreeBoardDto;
import ino.web.freeBoard.service.FreeBoardService;

@Controller
public class FreeBoardController {

	@Autowired
	private FreeBoardService freeBoardService;

	@RequestMapping("/main.ino")
	public ModelAndView main(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		List<FreeBoardDto> list = freeBoardService.freeBoardList();

		mav.setViewName("boardMain");
		mav.addObject("freeBoardList",list);
		return mav;
	}

	@RequestMapping("/freeBoardInsert.ino")
	public String freeBoardInsert(FreeBoardDto dto){
		return "freeBoardInsert";
	}
	
	@ResponseBody
	@RequestMapping("/freeBoardInsertPro.ino")
	public Map<String, Object> freeBoardInsertPro(HttpServletRequest request, FreeBoardDto dto){
		Map<String, Object> nMap = new HashMap<String, Object>();
		
		try{
			freeBoardService.freeBoardInsertPro(dto);
			int num = freeBoardService.getNewNum(dto.getNum());
			
			nMap.put("result", true);
			nMap.put("num", num);
			return nMap;

		}catch (Exception e){
			nMap.put("result", false);
			nMap.put("error",e.getCause().getMessage());
		    return nMap;
		}
		
	}
	
	@RequestMapping("/freeBoardDetail.ino")
	public ModelAndView freeBoardDetail(HttpServletRequest request, @RequestParam("num") int num){
		FreeBoardDto freeBoardDto = freeBoardService.getDetailByNum(num);
		return new ModelAndView("freeBoardDetail", "freeBoardDto", freeBoardDto);
	}
	
	@ResponseBody
	@RequestMapping("/freeBoardModify.ino")
	public Map<String, Object> freeBoardModify(HttpServletRequest request, FreeBoardDto dto){
		Map<String, Object> mMap = new HashMap<String, Object>();
		
		try{
			freeBoardService.freeBoardModify(dto);
			
			mMap.put("result", true);
			return mMap;

		}catch (Exception e){
			mMap.put("result", false);
			mMap.put("error",e.getCause().getMessage());
		    return mMap;
		}
	}
	
	@ResponseBody
	@RequestMapping("/freeBoardDelete.ino")
	public Map<String, Object> FreeBoardDelete(HttpServletRequest request, @RequestParam("num") int num){
		Map<String, Object> dMap = new HashMap<String, Object>();
		
		try{
			freeBoardService.FreeBoardDelete(num);
			dMap.put("result", true);
			return dMap;

		}catch (Exception e){
			dMap.put("result", false);
			dMap.put("error",e.getCause().getMessage());
		    return dMap;
		}
	}
	
	@ResponseBody
	@RequestMapping("/freeBoardCheDelete.ino")
	public Map<String, Object> FreeBoardCheDelete(HttpServletRequest request, @RequestParam(value="checkArray[]") List <Integer> chboxList) {
		Map<String, Object> cMap = new HashMap<String, Object>();
		
		try{
			freeBoardService.FreeBoardCheDelete(chboxList);
			cMap.put("result", true);
			return cMap;

		}catch (Exception e){
			cMap.put("result", false);
			cMap.put("error",e.getCause().getMessage());
		    return cMap;
		}
	}
	
	
	
	
}





////controller


package ino.web.freeBoard.service;

import ino.web.freeBoard.dto.FreeBoardDto;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.SystemPropertyUtils;

@Service
public class FreeBoardService {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public List<FreeBoardDto> freeBoardList(){
		return sqlSessionTemplate.selectList("freeBoardGetList");
	}

	public void freeBoardInsertPro(FreeBoardDto dto){
		sqlSessionTemplate.insert("freeBoardInsertPro",dto);
	}

	public FreeBoardDto getDetailByNum(int num){
		return sqlSessionTemplate.selectOne("freeBoardDetailByNum", num);
	}

	public int getNewNum(int num){
		return sqlSessionTemplate.selectOne("freeBoardNewNum");
	}

	public void freeBoardModify(FreeBoardDto dto){
		sqlSessionTemplate.update("freeBoardModify", dto);
	}

	public void FreeBoardDelete (int num) {
		sqlSessionTemplate.delete("freeBoardDelete", num);
	}
	
	public void FreeBoardCheDelete (List<Integer> chboxList) {
		for(int i = 0; i < chboxList.size(); i++) {
			sqlSessionTemplate.delete("freeBoardCheDelete", chboxList.get(i));
		}
	}

}

////service




<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<div>
		<h1>자유게시판</h1>
	</div>
	<div style="width:650px;" align="right">
		<a href="./freeBoardInsert.ino">글쓰기</a>
	</div>
	<hr style="width: 600px;">
	
	<div style="padding-bottom: 10px;">
		<table border="1">
			<thead>
				<tr>
					<td style="width: 55px; padding-left: 30px;" align="center"> <input type="checkbox" id="all-chk">타입</td>
					<td style="width: 50px; padding-left: 10px;" align="center">글번호</td>
					<td style="width: 125px;" align="center">글제목</td>
					<td style="width: 48px; padding-left: 50px;" align="center">글쓴이</td>
					<td style="width: 100px; padding-left: 95px;" align="center">작성일시</td>
				</tr>
			</thead>
		</table>
	</div>
	<hr style="width: 600px;">

	<div>
		<table border="1">
			<tbody id="tb" name="tb">
					<c:forEach var="dto" items="${freeBoardList }">
						<tr>
							<td style="width: 55px; padding-left: 30px;" align="center"><input type="checkbox" class="cla-chk" value="${dto.num}">${dto.codeType }</td>
							<td style="width: 50px; padding-left: 10px;" align="center">${dto.num }</td>
							<td style="width: 125px; "" align="center"><a href="./freeBoardDetail.ino?num=${dto.num }">${dto.title }</a></td>
							<td style="width: 48px; padding-left: 50px;" align="center">${dto.name }</td>
							<td style="width: 100px; padding-left: 95px;" align="center">${dto.regdate }</td>
						<tr>
					</c:forEach>
			</tbody>
		</table>
		<input id="delete" type="button" value="삭제">
	</div>
	
</body>



<script>

	//체크박스 전체선택 및 해제 클릭 이벤트
	$("#all-chk").on("click",function() {
		if($("input:checkbox[id='all-chk']").is(":checked")) {
			$(".cla-chk").prop("checked", true);	//체크박스 전체선택
		}else {
			$(".cla-chk").prop("checked", false);	//체크박스 전체해제
		}
	});	// 체크박스 전체 클릭이벤트 끝
	 
	//체크박스 선택 및 전체삭제 이벤트
	$("#delete").on("click", function() {
		var checkArray = new Array();
		
		$("input:checked[class=cla-chk]:checked").each(function () {
			checkArray.push(parseInt($(this).val()));
		});// 선택된 체크박스 값 배열에 넣기
		
		var check = confirm("삭제하시겠습니까?");
		
		if(check) {
			$.ajax({
				url : "./freeBoardCheDelete.ino",
				type : "post",
				data : {checkArray : checkArray
						},
				success : function(cMap){
					if(cMap.result) {
						alert("삭제되었습니다.");
						location.href = "./main.ino"
					}else {
						alert(cMap.error);
					}
				},
				error : function(XHR, status, error) {
				console.error(status + " : " + error);
				}
			});
		}
	});

	
</script>

</html>

/////main



<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>

	<div>
		<h1>자유게시판</h1>
	</div>
	<div style="width:650px;" align="right">
		<a href="./main.ino">리스트로</a>
	</div>
	<hr style="width: 600px">

	<form name="insertForm">
		<input type="hidden" name="num" value="${freeBoardDto.num}" />
		<table border="1">
			<tbody>
				<tr>
				
					<td style="width: 150px;" align="center">타입 :</td>
					<td style="width: 400px;">
						<select id="selectBox">
							<option value="01" ${freeBoardDto.codeType == '01' ? 'selected="selected"' : '' }>자유</option>
							<option value="02" ${freeBoardDto.codeType == '02' ? 'selected="selected"' : ''}>익명</option>
							<option value="03" ${freeBoardDto.codeType == '03' ? 'selected="selected"' : ''}>QnA</option>
						</select>
					</td>
				</tr>
				<tr>
					<td style="width: 150px;"align="center">이름 :</td>
					<td style="width: 400px;"><input type="text" name="name" value="${freeBoardDto.name }" readonly/></td>
				</tr>
				<tr>
					<td style="width: 150px;"align="center">제목 :</td>
					<td style="width: 400px;"><input type="text" name="title"  value="${freeBoardDto.title }"/></td>
				</tr>
				<tr>
					<td style="width: 150px;"align="center">내용 :</td>
					<td style="width: 400px;"><textarea name="content" rows="25" cols="65"  >${freeBoardDto.content }</textarea></td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td></td>
					<td align="right">
					<input type="button" id="modify" value="수정" >
					<input type="button" id="delete" value="삭제" >
					<input type="button" value="취소" onclick="location.href='./main.ino'">
					&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</tfoot>
		</table>
	</form>



</body>

<script>

	//수정
	$("#modify").on("click", function() {
		var codeType = $("#selectBox option:selected").val();
		var num = $("input[name=num]").val();
		var title = $("input[name=title]").val();
		var content = $("textarea[name=content]").val();
		
		if(title == '') {
			alert("제목을 입력해주세요.");
		}else if(content == '') {
			alert("내용을 입력해주세요.");
		}else {
			var check = confirm("수정하시겠습니까?");
			
			if(check) {
				$.ajax({
					url : "./freeBoardModify.ino",
					type : "post",
					data : {codeType: codeType,
							num: num,
							title : title,
							content : content
							},
					success : function(mMap){
						if(mMap.result) {
							alert("수정되었습니다.");
							var select = confirm("메인화면으로 가시겠습니까?");
	
							if(select) {
								location.href = "./main.ino"
							}else {
								location.href = "./freeBoardDetail.ino?num=" + num									
							}
						}else {
							alert(mMap.error);
						}
					},
					error : function(XHR, status, error) {
					console.error(status + " : " + error);
					}
				});	
			} 
		} 
	}); //modify 수정이벤트 끝
	
	//삭제
	$("#delete").on("click", function() {
		var num = $("input[name=num]").val();
		var check = confirm("삭제하시겠습니까?");
		
		if(check) {
			$.ajax({
				url : "./freeBoardDelete.ino",
				type : "post",
				data : {num: num,
						},
				success : function(dMap){
					if(dMap.result) {
						alert("삭제되었습니다.");
						location.href = "./main.ino"
					}else {
						alert(dMap.error);
					}
				},
				error : function(XHR, status, error) {
				console.error(status + " : " + error);
				}
			});	
		}
	});	//delete 버튼이벤트 끝
	
	
	
</script>

</html>



////// detail




<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<div>
		<h1>자유게시판</h1>
	</div>
	<div style="width:650px;" align="right">
		<a href="./main.ino">리스트로</a>
	</div>
	<hr style="width: 600px">

	<form action="./freeBoardInsertPro.ino">

		<table border="1">
			<tbody>
				<tr>
					<td style="width: 150px;" align="center">타입 :</td>
					<td style="width: 400px;">
						<select id="selectBox">
							<option value="01">자유</option>
							<option value="02">익명</option>
							<option value="03">QnA</option>
						</select>
					</td>
				</tr>
				<tr>
					<td style="width: 150px;"align="center">이름 :</td>
					<td style="width: 400px;"><input type="text" name="name" value="" /></td>
				</tr>
				<tr>
					<td style="width: 150px;"align="center">제목 :</td>
					<td style="width: 400px;"><input type="text" name="title" value="" /></td>
				</tr>
				<tr>
					<td style="width: 150px;"align="center">내용 :</td>
					<td style="width: 400px;"><textarea name="content" rows="25" cols="65" ></textarea></td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td></td>
					<td align="right">
					<input type="button" id="write" value="글쓰기">
					<input type="button" value="다시쓰기" onclick="reset()">
					<input type="button" value="취소" onclick="">
					&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</tfoot>
		</table>

	</form>



</body>


<script>
	$("#write").on("click", function() {
		var name = $("input[name=name]").val();
		var title = $("input[name=title]").val();
		var content = $("textarea[name=content]").val();
		var codeType = $("#selectBox option:selected").val();
		
		if(name == '') {
			alert("이름을 입력해주세요.");
		}else if(title == '') {
			alert("제목을 입력해주세요.");
		}else if(content == '') {
			alert("내용을 입력해주세요.");
		}else if(name != null && title != null && content != null) {
			
			var check = confirm("저장하시겠습니까?");
			
			if(check) {
				$.ajax({
					url : "./freeBoardInsertPro.ino",
					type : "post",
					//contentType : "application/json",
					data : {codeType: codeType,
							name: name,
							title: title,
							content: content},
					//dataType : "json",
					success : function(nMap){
						
						if(nMap.result) {
							alert("저장되었습니다.");
							var select = confirm("메인화면으로 가시겠습니까?");

							if(select) {
								location.href = "./main.ino"
							}else {
								location.href = "./freeBoardDetail.ino?num=" + nMap.num										
							}
						}else {
							alert(nMap.error);
						}
					},
					error : function(XHR, status, error) {
					console.error(status + " : " + error);
					}
					});	
			}
			
		}
		
		
	});
</script>

</html>

//// insert



<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="ino.web.freeBoard.mapper.FreeBoardMapper">


	<select id="freeBoardGetList" resultType="freeBoardDto" > <!-- resultType="ino.web.board.dto.BoardDto" -->
		SELECT	DECODE(CODE_TYPE, '01', '자유', '02', '익명', '03', 'QnA') codeType, 
				NUM, NAME, TITLE, CONTENT, TO_CHAR(REGDATE , 'YYYY/MM/DD') AS REGDATE FROM FREEBOARD
	</select>

	<insert id="freeBoardInsertPro" parameterType="freeBoardDto">
		INSERT INTO FREEBOARD(CODE_TYPE, NUM, TITLE, NAME, REGDATE, CONTENT)
		VALUES(#{codeType}, SEQ_NUM.NEXTVAL, #{title}, #{name}, SYSDATE, #{content})
	</insert>

	<select id="freeBoardDetailByNum" resultType="freeBoardDto" parameterType="int">
		SELECT CODE_TYPE codeType, NUM, TITLE, NAME, TO_CHAR(REGDATE,'YYYY/MM/DD') REGDATE, CONTENT FROM FREEBOARD
		WHERE NUM=#{num}
	</select>

	<select id="freeBoardNewNum" resultType="int">
		SELECT MAX(NUM)
		FROM FREEBOARD
	</select>

	<update id="freeBoardModify" parameterType="freeBoardDto">
		UPDATE FREEBOARD
		SET 	CODE_TYPE = #{codeType}
				, TITLE = #{title}
				, CONTENT = #{content}
		WHERE NUM = #{num}

	</update>

	<update id="freeBoardDelete" parameterType="int">
		DELETE FROM FREEBOARD
		WHERE NUM
		= #{num}
	</update>
	
	<select id="freeBoardCheDelete" parameterType="int">
		DELETE FROM FREEBOARD
		WHERE NUM
		= #{num}
	</select>

</mapper>




xml

