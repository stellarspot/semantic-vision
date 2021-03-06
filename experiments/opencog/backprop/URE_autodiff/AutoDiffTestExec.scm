(use-modules (ice-9 readline)) (activate-readline)
(add-to-load-path "/usr/share/opencog/scm") ; !!!! replace on your opebcog scm folder
(add-to-load-path ".")

(use-modules (opencog))
(use-modules (opencog query))
(use-modules (opencog exec))
(use-modules (opencog rule-engine))



(DefineLink(DefinedSchemaNode "GradientMul")
           (BindLink
            (VariableList
             (VariableNode "$F1")
             (VariableNode "$F2"))
            (AndLink
             (ListLink
              (ConceptNode "Gradient")
              (AndLink
               (VariableNode "$F1")
               (VariableNode "$F2")))
             (JoinLink
              (AndLink
               (ListLink
                (ConceptNode "Gradient") (VariableNode "$F1"))
               (VariableNode "$F2"))
              (AndLink
               (ListLink
                (ConceptNode "Gradient") (VariableNode "$F2"))
               (VariableNode "$F1")))))) 

(define SRC (ListLink (ConceptNode "Gradient")
          (AndLink
           (JoinLink
            (NumberNode 3)(NumberNode 3))
           (JoinLink
            (NumberNode 3)(NumberNode 3)))))

(ExecutionLink
   (SchemaNode "URE:maximum-iterations")
   (ConceptNode "my-rule-base")
   (NumberNode "10000"))

(Inheritance (Concept "my-rule-base") (Concept "URE"))

(Member (DefinedSchema "GradientMul" (stv 0.4 1)) (Concept "my-rule-base"))

(define (my-forward-chainer SRC) (cog-fc (Concept "my-rule-base") SRC))

(GetLink (VariableNode "$X"))
