package com.spring.mvc.chap05.service;

import com.spring.mvc.chap05.common.Search;
import com.spring.mvc.chap05.dto.request.BoardWriteRequestDTO;
import com.spring.mvc.chap05.dto.response.BoardDetailResponseDTO;
import com.spring.mvc.chap05.dto.response.BoardListResponseDTO;
import com.spring.mvc.chap05.entity.Board;
import com.spring.mvc.chap05.mapper.BoardMapper;
import com.spring.mvc.util.LoginUtils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    //    private final BoardRepository mapper;
    private final BoardMapper mapper; // mybatis가 우리가 만든 xml을 클래스로 변환해서 객체를 주입해 줌.

    public void register(BoardWriteRequestDTO dto, HttpSession session) {
        Board board = new Board(dto); // dto를 entity로 변환
        // 이제는 화면단에서 작성자가 전달되지 않음.
        // 세션에서 현재 로그인 중인 사용자의 아이디를 얻어와서 따로 세팅.
        board.setWriter(LoginUtils.getCurrentLoginMemberAccount(session));
        mapper.save(board);
    }

    // mapper로부터 전달받은 entity List를 DTO List로 변환해서 컨트롤러에게 리턴.
    public List<BoardListResponseDTO> getList(Search page) {
        List<BoardListResponseDTO> dtoList = new ArrayList<>();
        List<Board> boardList = mapper.findAll(page);
        for (Board board : boardList) {
            BoardListResponseDTO dto = new BoardListResponseDTO(board);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public BoardDetailResponseDTO getDetail(int bno) {
        // 상세보기니까 조회수를 하나 올려주는 처리를 해야 한다.
        mapper.updateViewCount(bno);

        Board board = mapper.findOne(bno);
        return new BoardDetailResponseDTO(board);
    }

    public void delete(int bno) {
        mapper.delete(bno);
    }

    public int getCount(Search page) {
        return mapper.getCount(page);
    }
}












