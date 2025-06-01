//package com.sistema.avaliacao.service;
//
//import com.sistema.avaliacao.exceptions.APIException;
//import com.sistema.avaliacao.model.Avaliacao;
//import com.sistema.avaliacao.payload.dto.AvaliacaoDTO;
//import com.sistema.avaliacao.payload.response.AvaliacaoResponse;
//import com.sistema.avaliacao.repositories.AlunoRepository;
//import com.sistema.avaliacao.repositories.AvaliacaoRepository;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class AvaliacaoServiceImplement<T extends Avaliacao, D extends AvaliacaoDTO> implements AvaliacaoService<D> {
//
//    @Autowired
//    private AvaliacaoRepository<T> avaliacaoRepository;
//
//    @Autowired
//    private AlunoRepository alunoRepository;
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    private final Class<T> entityClass;
//    private final Class<D> dtoClass;
//
//    public AvaliacaoServiceImplement(Class<T> entityClass, Class<D> dtoClass) {
//        this.entityClass = entityClass;
//        this.dtoClass = dtoClass;
//    }
//
//    @Override
//    public AvaliacaoResponse<D> getAllAvaliacoes(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
//        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
//                ? Sort.by(sortBy).ascending()
//                : Sort.by(sortBy).descending();
//
//        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
//        Page<T> avaliacaoPage = avaliacaoRepository.findAll(pageDetails);
//        List<T> avaliacoes = avaliacaoPage.getContent();
//
//        if (avaliacoes.isEmpty()) {
//            throw new APIException("Nenhuma avaliação adicionada até agora.");
//        }
//
//        List<D> avaliacaoDTOS = avaliacoes.stream()
//                .map(avaliacao -> modelMapper.map(avaliacao, dtoClass))
//                .toList();
//
//        AvaliacaoResponse<D> avaliacaoResponse = new AvaliacaoResponse<>();
//        avaliacaoResponse.setContent(avaliacaoDTOS);
//        avaliacaoResponse.setPageNumber(avaliacaoPage.getNumber());
//        avaliacaoResponse.setPageSize(avaliacaoPage.getSize());
//        avaliacaoResponse.setTotalElements(avaliacaoPage.getTotalElements());
//        avaliacaoResponse.setTotalPages(avaliacaoPage.getTotalPages());
//        avaliacaoResponse.setLastPage(avaliacaoPage.isLast());
//
//        return avaliacaoResponse;
//    }
//
//
////    @Override
////    public List<D> getAvaliacoesDoAluno(String matricula) {
////        List<T> avaliacoes = avaliacaoRepository.findByAlunoMatriculaAcademica(matricula);
////        if (avaliacoes.isEmpty()) {
////            throw new APIException("O aluno com a matrícula " + matricula + " não possui avaliações.");
////        }
////        return avaliacoes.stream()
////                .map(avaliacao -> modelMapper.map(avaliacao, dtoClass))
////                .toList();
////    }
////
////    @Override
////    public D createAvaliacao(D avaliacaoDTO) {
////        T avaliacao = modelMapper.map(avaliacaoDTO, entityClass);
////        T saved = avaliacaoRepository.save(avaliacao);
////        return modelMapper.map(saved, dtoClass);
////    }
////
////    @Override
////    public void deleteAvaliacao(Long id) {
////        T avaliacao = avaliacaoRepository.findById(id)
////                .orElseThrow(() -> new ResourceNotFoundException("Avaliação", "id", id));
////        avaliacaoRepository.delete(avaliacao);
////    }
////}
